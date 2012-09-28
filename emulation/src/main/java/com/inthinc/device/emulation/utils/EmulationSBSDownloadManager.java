package com.inthinc.device.emulation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.inthinc.device.emulation.interfaces.SbsHessianInterface;
import com.inthinc.device.hessian.tcp.HessianException;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.sbs.SbsUpdater;
import com.inthinc.sbs.downloadmanager.SbsDownloadAction;
import com.inthinc.sbs.downloadmanager.SbsDownloadManager;
import com.inthinc.sbs.simpledatatypes.VisitedMap;
import com.inthinc.sbs.utils.AbstractSbsMapLoader;
import com.inthinc.sbs.utils.MapLoader;
import com.inthinc.sbs.utils.SbsUtils;

public class EmulationSBSDownloadManager implements SbsDownloadManager {

    private SbsHessianInterface proxy;
    private String mcmid;
    private SbsUpdater sbsUpdater;

    public static final String NAME_KEY = "f";
    public static final String VERSION_KEY = "v";
    public final static String prefix = "target/sbsMaps";
    private final static Set<Integer> unrealMaps = new HashSet<Integer>();

    private final StringBuilder sb = new StringBuilder(256);
    private Map<Integer, VisitedMap> visitedMaps;

    public EmulationSBSDownloadManager(SbsHessianInterface proxy, String mcmid) {
        this.proxy = proxy;
        this.mcmid = mcmid;
        this.visitedMaps = new HashMap<Integer, VisitedMap>();
        MapLoader.manager.set(this);
    }

    @Override
    public void addMap(int fileAsInt, int version) {
        VisitedMap v = new VisitedMap(fileAsInt, version, 1);
        if (!visitedMaps.containsKey(fileAsInt)) {
            visitedMaps.put(fileAsInt, v);
        }
    }

    @Override
    public int getEntryCount(int fileAsInt) {
        if (visitedMaps.containsKey(fileAsInt)) {
            return visitedMaps.get(fileAsInt).entryCount;
        }
        return 0;
    }

    @Override
    public void incVisitedMapEntryCount(int fileAsInt) {
        if (visitedMaps.containsKey(fileAsInt)) {
            VisitedMap v = visitedMaps.get(fileAsInt);
            v.entryCount++;
        }

    }

    @Override
    public boolean mapHasBeenVisited(int fileAsInt) {
        return visitedMaps.containsKey(fileAsInt);
    }

    @Override
    public void removeMap(int fileAsInt) {
        visitedMaps.remove(fileAsInt);

    }

    @Override
    public void setSbsUpdater(SbsUpdater sbsUpdater) {
        this.sbsUpdater = sbsUpdater;
    }

    @Override
    public boolean queueAction(SbsDownloadAction action) {
        switch (action.name) {
            case checkSbsEditNG:
                checkSbsEditNG(action.b, action.fileAsInt, action.cv);
                break;
            case checkSbsSubscribedNG:
                checkSbsSubscribedNG(action.b, action.fileAsInt);
                break;
            case getSbsBase:
                getSbsBase(action.fileAsInt, action.b);
                break;
            case getSbsEditNG:
                getSbsEditNG(action.fileAsInt, action.b, action.cv, action.nv);
                break;
        }
        return true;
    }

    private List<Map<String, Object>> checkSbsSubscribedNG(int b, int fileAsInt) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("b", b);
        args.put("lai", SbsUtils.createLatitudeIndex(fileAsInt));
        args.put("loi", SbsUtils.createLongitudeIndex(fileAsInt));
        args.put("w", 80);
        List<Map<String, Object>> reply = proxy.checkSbsSubscribedNG(mcmid, args);
        postProcessSubscriptionList(reply, b);
        return reply;
    }

    public boolean postProcessSubscriptionList(List<Map<String, Object>> list, int base) {
        for (Map<String, Object> next : list) {
            Integer fai = (Integer) next.get(NAME_KEY);
            Integer v = (Integer) next.get(VERSION_KEY);

            int baseCheckResult = checkBaselineVersion(fai, base);

            if (baseCheckResult == 0) {
                getSbsBase(fai, base);
            } else if (baseCheckResult == 1) {
                if (!checkExceptionVersion(fai, v)) {
                    getSbsEditNG(fai, base, 0, v);
                }
            }
        }
        return true;
    }

    private byte[] checkbuf = new byte[33];

    private boolean checkExceptionVersion(Integer fai, Integer v) {
        sb.delete(0, sb.length());
        sb.append("/target/sbsMaps");
        sb.append(AbstractSbsMapLoader.EXMAPV2);
        sb.append(SbsUtils.createMapFilename(fai, false));
        File f = new File(sb.toString());
        if (!f.exists()) {
            if (v != 0) {
                return false;
            } else {
                return true;
            }
        }

        return checkVersion(f, checkbuf, v);
    }

    /**
     * Check a file's version against the one provided.
     * 
     * @param f
     * @param version
     *            expected version
     * @return true if the version is greater than or equal to the expected version.
     */
    public boolean checkVersion(File f, byte buf[], int version) {
        FileInputStream fis = null;
        boolean res = false;

        if (buf == null) {
            return false;
        }

        Arrays.fill(buf, (byte) 0);
        try {
            fis = new FileInputStream(f);

            fis.read(buf);
            if (buf[0] >= version) {
                res = true;
            }
        } catch (FileNotFoundException e) {
            Log.error(String.format("failed to load header: %s", e.getLocalizedMessage()));
        } catch (IOException ioe) {
            Log.error(String.format("failed to load header: %s", ioe.getLocalizedMessage()));
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    Log.error(String.format("failed to close after load header: %s", e.getLocalizedMessage()));
                }
            }
        }

        return res;
    }

    private int checkBaselineVersion(Integer fai, int base) {
        sb.delete(0, sb.length());
        sb.append(prefix);
        sb.append(AbstractSbsMapLoader.SBSV2);
        sb.append(SbsUtils.createMapFilename(fai, false));
        File f = new File(sb.toString());
        if (!f.exists()) {
            sb.delete(0, sb.length());
            sb.append(prefix);
            sb.append(SbsUtils.createMapFilename(fai, true));
            f = new File(sb.toString());
            if (!f.exists()) {
                return -1;
            }
        }

        if (checkVersion(f, checkbuf, base)) {
            return 1;
        } else {
            return 0;
        }
    }

    public Map<String, Object> getSbsBase(int fileAsInt, int base) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("b", base);
        args.put("f", fileAsInt);
        try {
            Map<String, Object> reply = proxy.getSbsBase(mcmid, args);
            sbsUpdater.saveBasemap(fileAsInt, reply);
            return reply;
        } catch (HessianException e) {
            if (e.getErrorCode() == 304) {
                unrealMaps.add(fileAsInt);
            }
            Log.debug("%s", "actionGetSbsBase:  Null result returned");
            return new HashMap<String, Object>();
        }
    }

    public void checkSbsEditNG(int baseVersion, int fileAsInt, int currentVersion) {
        if (unrealMaps.contains(fileAsInt)) {
            return;
        }
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("b", baseVersion);
        args.put("f", fileAsInt);
        args.put("cv", currentVersion);
        maps.add(args);
        List<Map<String, Object>> list = proxy.checkSbsEditNG(mcmid, maps);
        if (list.isEmpty()){
            
        }
        for (Map<String, Object> element : list) {
            Integer fileAsIntL = (Integer) element.get("f");
            Integer baseVersionL = (Integer) element.get("b");
            Integer newVersionL = (Integer) element.get("v");
            getSbsEditNG(fileAsIntL, baseVersionL, currentVersion, newVersionL);
        }
    }

    private Map<String, Object> getSbsEditNG(Integer fileAsInt, Integer baseVersion, int currentVersion, Integer newVersion) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("b", baseVersion);
        args.put("f", fileAsInt);
        args.put("cv", currentVersion);
        args.put("nv", newVersion);
        try {
            Map<String, Object> results = proxy.getSbsEditNG(mcmid, args);
            sbsUpdater.saveBasemap(fileAsInt, results);
            sbsUpdater.mergeExceptionmap(fileAsInt, baseVersion, results);
            return results;
        } catch (HessianException e) {
            if (e.getErrorCode() == 304) {
                unrealMaps.add(fileAsInt);
                Log.info("Could not load map %d, Map=%s", fileAsInt, args);
            }
            return new HashMap<String, Object>();
        }
    }

}
