package com.inthinc.device.emulation.interfaces;

import java.util.Map;

public interface DownloadService extends HessianService {

    Map<String, Object> createDownload(Map<String, Object> map);

}
