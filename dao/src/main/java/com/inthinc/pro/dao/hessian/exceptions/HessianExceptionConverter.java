package com.inthinc.pro.dao.hessian.exceptions;

import java.util.HashMap;
import java.util.Map;

public class HessianExceptionConverter
{
    private static final Map<Integer, String> HESSIAN_CONNECTION_EXCEPTION_MAP = new HashMap<Integer, String>();
    private static final Map<Integer, String> HESSIAN_EXCEPTION_MAP = new HashMap<Integer, String>();
    private static final Map<Integer, String> REMOTESERVER_EXCEPTION_MAP = new HashMap<Integer, String>();
    private static final Map<Integer, String> PROXY_EXCEPTION_MAP = new HashMap<Integer, String>();

    static
    {
        HESSIAN_CONNECTION_EXCEPTION_MAP.put(101, "ConnNotConnected.....    An attempt to communicate with a socket that is not connected.");
        HESSIAN_CONNECTION_EXCEPTION_MAP.put(102, "ConnBadCall..........    A call to a function did not supply all necessary parameters.");
        HESSIAN_CONNECTION_EXCEPTION_MAP.put(103, "ConnInterupted.......    Communication over a socket was interupted.");
        HESSIAN_CONNECTION_EXCEPTION_MAP.put(104, "ConnTimeout..........    Communication over a socket timed out.");
        HESSIAN_CONNECTION_EXCEPTION_MAP.put(105, "ConnBadWrite.........    An attempt to write to a socket failed.");
        HESSIAN_CONNECTION_EXCEPTION_MAP.put(106, "ConnBadRead..........    An attempt to read from a socket failed.");
        HESSIAN_CONNECTION_EXCEPTION_MAP.put(107, "ConnMissCRLF.........    An attempt to read a line failed, no CR LF.");
        HESSIAN_CONNECTION_EXCEPTION_MAP.put(108, "ConnFailedAccept.....    An attempt to accept a socket connection failed.");
        HESSIAN_CONNECTION_EXCEPTION_MAP.put(109, "ConnFailedHostname...    An attempt to lookup a hostname failed.");
        HESSIAN_CONNECTION_EXCEPTION_MAP.put(110, "ConnFailedSocket.....    An attempt to obtain a socket failed.");
        HESSIAN_CONNECTION_EXCEPTION_MAP.put(111, "ConnFailedFcntl......    A call to fcntl() failed.");
        HESSIAN_CONNECTION_EXCEPTION_MAP.put(112, "ConnEOF..............    End Of File encountered.");

        HESSIAN_EXCEPTION_MAP.put(201, "HessianEnd...........    The end of a Hessian statement was reached.");
        HESSIAN_EXCEPTION_MAP.put(202, "HessianBadCall.......    A call to a function did not supply all necessary parameters.");
        HESSIAN_EXCEPTION_MAP.put(203, "HessianNotImplemented    Encountered a Hessian object that is not implemented.");
        HESSIAN_EXCEPTION_MAP.put(204, "HessianNoMemory......    Insufficient memory to create a Hessian object.");
        HESSIAN_EXCEPTION_MAP.put(205, "HessianNotOpened.....    An attempt to read/write from/to a socket that is not opened.");
        HESSIAN_EXCEPTION_MAP.put(206, "HessianEOF...........    End Of File encountered.");
        HESSIAN_EXCEPTION_MAP.put(207, "HessianBadIO.........    An attempt to read/write from/to a socket failed.");
        HESSIAN_EXCEPTION_MAP.put(208, "HessianExpectCall....    Encountered something other than the expected Hessian 'call' object.");
        HESSIAN_EXCEPTION_MAP.put(209, "HessianExpectMethod..    Encountered something other than the expected Hessian 'method' object.");
        HESSIAN_EXCEPTION_MAP.put(210, "HessianExpectReply...    Encountered something other than the expected Hessian 'reply' object.");
        HESSIAN_EXCEPTION_MAP.put(211, "HessianExpectEnd.....    Encountered something other than the expected Hessian 'end' object.");
        HESSIAN_EXCEPTION_MAP.put(212, "HessianExpectBool....    Encountered something other than the expected Hessian 'Boolean' object.");
        HESSIAN_EXCEPTION_MAP.put(213, "HessianExpectInt.....    Encountered something other than the expected Hessian 'Integer' object.");
        HESSIAN_EXCEPTION_MAP.put(214, "HessianExpectLong....    Encountered something other than the expected Hessian 'Long' object.");
        HESSIAN_EXCEPTION_MAP.put(215, "HessianExpectDouble..    Encountered something other than the expected Hessian 'Double' object.");
        HESSIAN_EXCEPTION_MAP.put(216, "HessianExpectDate....    Encountered something other than the expected Hessian 'Date' object.");
        HESSIAN_EXCEPTION_MAP.put(217, "HessianExpectString..    Encountered something other than the expected Hessian 'String' object.");
        HESSIAN_EXCEPTION_MAP.put(218, "HessianExpectBinary..    Encountered something other than the expected Hessian 'Binary' object.");
        HESSIAN_EXCEPTION_MAP.put(219, "HessianExpectXml.....    Encountered something other than the expected Hessian 'Xml' object.");
        HESSIAN_EXCEPTION_MAP.put(220, "HessianExpectList....    Encountered something other than the expected Hessian 'List' object.");
        HESSIAN_EXCEPTION_MAP.put(221, "HessianExpectMap.....    Encountered something other than the expected Hessian 'Map' object.");
        HESSIAN_EXCEPTION_MAP.put(222, "HessianExpectRef.....    Encountered something other than the expected Hessian 'Reference' object.");
        HESSIAN_EXCEPTION_MAP.put(223, "HessianExpectFault...    Encountered something other than the expected Hessian 'Fault' object.");
        HESSIAN_EXCEPTION_MAP.put(224, "HessianBadUtf8.......    Encountered improperly formed UTF8 characters.");
        HESSIAN_EXCEPTION_MAP.put(225, "HessianUnknownType...    Unknown Hessian object type encountered.");
        HESSIAN_EXCEPTION_MAP.put(226, "HessianStringTooLong.    Attempt to send a string thats too long.");
        HESSIAN_EXCEPTION_MAP.put(227, "HessianBinaryTooLong.    Attempt to send binary data thats too long.");
        HESSIAN_EXCEPTION_MAP.put(228, "HessianTimeout.......    A timeout occured");

        REMOTESERVER_EXCEPTION_MAP.put(301, "TSCodeNoMemory.......    Insufficient memory for the server.");
        REMOTESERVER_EXCEPTION_MAP.put(302, "TSCodeNoDB...........    The server is not able to get a Database connection.");
        REMOTESERVER_EXCEPTION_MAP.put(303, "TSCodeNoStmt.........    The server is not able to get a Database statement handle.");
        REMOTESERVER_EXCEPTION_MAP.put(304, "TSCodeNoData.........    The Database fetch did not return data.");
        REMOTESERVER_EXCEPTION_MAP.put(305, "TSCodeNoMethod.......    The request method is not found on the server.");
        REMOTESERVER_EXCEPTION_MAP.put(306, "TSCodePrepare........    A Database prepare statement call failed.");
        REMOTESERVER_EXCEPTION_MAP.put(307, "TSCodeExecute........    A Database execute call failed.");
        REMOTESERVER_EXCEPTION_MAP.put(308, "TSCodeBindParam......    A Database bind parameters call failed.");
        REMOTESERVER_EXCEPTION_MAP.put(309, "TSCodeBindResult.....    A Database bind results call failed.");
        REMOTESERVER_EXCEPTION_MAP.put(310, "TSCodeStoreResult....    A Database store results call failed.");
        REMOTESERVER_EXCEPTION_MAP.put(311, "TSCodeFreeResult.....    A Database free results call failed.");
        REMOTESERVER_EXCEPTION_MAP.put(312, "TSCodeParamNumof.....    Did not receive the expected number of parameters.");
        REMOTESERVER_EXCEPTION_MAP.put(313, "TSCodeParamBad.......    A parameter of the wrong type was encountered.");
        REMOTESERVER_EXCEPTION_MAP.put(314, "TSCodeParamMissReq...    Did not receive all the required parameters.");
        REMOTESERVER_EXCEPTION_MAP.put(315, "TSCodePassNotMatch...    The password did not match the one for this user.");
        REMOTESERVER_EXCEPTION_MAP.put(316, "TSCodeAcctDisabled...    The account is disabled.");
        REMOTESERVER_EXCEPTION_MAP.put(317, "TSCodeBadNote........    A bad note was encountered.");
        REMOTESERVER_EXCEPTION_MAP.put(318, "TSCodeMissId.........    Missing ID.");
        REMOTESERVER_EXCEPTION_MAP.put(319, "TSCodeNotConnected...    Not connected.");
        REMOTESERVER_EXCEPTION_MAP.put(320, "TSCodeInternal.......    Internal error.");
        REMOTESERVER_EXCEPTION_MAP.put(321, "TSCodeRange..........    Data out of range.");
        REMOTESERVER_EXCEPTION_MAP.put(322, "TSCodeDuplicate......    Executing the query would create a duplicate.");
        REMOTESERVER_EXCEPTION_MAP.put(323, "TSCodeNoDriver.......    The McmID is not mapped to a driver.");
        REMOTESERVER_EXCEPTION_MAP.put(324, "TSCodeDuplicateUser..    An attempt to use an existing username.");
        REMOTESERVER_EXCEPTION_MAP.put(325, "TSCodeDuplicateEmail.    An attempt to use an existing email address.");
        REMOTESERVER_EXCEPTION_MAP.put(326, "TSCodeDuplicateMcmid.    An attempt to use an existing mcmid");
        REMOTESERVER_EXCEPTION_MAP.put(327, "TSCodeDataMissReq....    The data required missing from the database.");
        REMOTESERVER_EXCEPTION_MAP.put(331, "TSCodeDuplicate...       Duplicate RFID.");
        
        PROXY_EXCEPTION_MAP.put(401, "ProxyNoMem...........    Insufficient memory for the proxy server.");
        PROXY_EXCEPTION_MAP.put(402, "ProxyNoDB............    The server is not able to get a Database connection.");
        PROXY_EXCEPTION_MAP.put(403, "ProxyNoData..........    The database fetch did not return data.");
        PROXY_EXCEPTION_MAP.put(404, "ProxyPrepare.........    A database prepare statement call failed.");
        PROXY_EXCEPTION_MAP.put(405, "ProxyExecute.........    A database execute call failed.");
        PROXY_EXCEPTION_MAP.put(406, "ProxyBind............    A database bind call failed.");
        PROXY_EXCEPTION_MAP.put(407, "ProxyBadParam........    A parameter of the wrong type was encountered.");
        PROXY_EXCEPTION_MAP.put(408, "ProxyTargetConn......    The server could not connect to the target server.");
        PROXY_EXCEPTION_MAP.put(409, "ProxySelect..........    A low-level system call to select failed.");
        PROXY_EXCEPTION_MAP.put(410, "ProxyTargetRead......    Failed to read from the Target machine.");
        PROXY_EXCEPTION_MAP.put(411, "ProxyTargetWrite.....    Failed to write to the Target machine.");
        PROXY_EXCEPTION_MAP.put(412, "ProxyClientRead......    Failed to read from the client.");
        PROXY_EXCEPTION_MAP.put(413, "ProxyClientWrite.....    Failed to write to the client.");
        PROXY_EXCEPTION_MAP.put(414, "ProxyUnkMcmid........    This mcmid is unknown to the system.");
        PROXY_EXCEPTION_MAP.put(415, "ProxyMisMcmid........    Hessian call must have an mcmid or driverID.");
        PROXY_EXCEPTION_MAP.put(416, "ProxyMisSiloID.......    Response from custServices(getSilos) is missing a siloID.");
        PROXY_EXCEPTION_MAP.put(417, "ProxySiloNotAvail....    No machines in the requested Silo are avilable right now.");
        PROXY_EXCEPTION_MAP.put(418, "ProxyCreateThread....    Failed to create a pthread to handle Silo load balancing.");
        PROXY_EXCEPTION_MAP.put(419, "ProxyTimeout.........   A timeout reading the clients hessian call.");
        PROXY_EXCEPTION_MAP.put(420, "ProxyAccess..........   Access denied, that host is not allowed to call that method.");
        PROXY_EXCEPTION_MAP.put(421, "ProxyUnkSilo.........   The siloID, associated with this call, is not registered.");
        PROXY_EXCEPTION_MAP.put(422, "ProxyUnkMethod.......   The method being called, is not registered.");
    }

    public static HessianException convert(String remoteMethodName, Object[] args, Integer errorCode)
    {
        if(errorCode == 304 || errorCode == 403)
        {
            return new EmptyResultSetException(REMOTESERVER_EXCEPTION_MAP.get(errorCode), remoteMethodName, errorCode);
        }
        else if(errorCode == 322)
        {
            return new DuplicateEntryException(REMOTESERVER_EXCEPTION_MAP.get(errorCode), remoteMethodName, errorCode);
        }
        else if(errorCode == 324)
        {
            return new DuplicateUsernameException(REMOTESERVER_EXCEPTION_MAP.get(errorCode), remoteMethodName, errorCode);
        }
        else if(errorCode == 325)
        {
            return new DuplicateEmailException(REMOTESERVER_EXCEPTION_MAP.get(errorCode), remoteMethodName, errorCode);
        }
        else if(errorCode == 326)
        {
            return new DuplicateIMEIException(REMOTESERVER_EXCEPTION_MAP.get(errorCode), remoteMethodName, errorCode);
        }
        else if (HESSIAN_CONNECTION_EXCEPTION_MAP.containsKey(errorCode))
        {
            return new HessianConnectionException(HESSIAN_CONNECTION_EXCEPTION_MAP.get(errorCode), remoteMethodName, errorCode);
        }
        else if (HESSIAN_EXCEPTION_MAP.containsKey(errorCode))
        {
            return new HessianException(HESSIAN_EXCEPTION_MAP.get(errorCode), remoteMethodName, errorCode);
        }
        else if (REMOTESERVER_EXCEPTION_MAP.containsKey(errorCode))
        {
            return new RemoteServerException(REMOTESERVER_EXCEPTION_MAP.get(errorCode), remoteMethodName, errorCode);
        }
        else if (PROXY_EXCEPTION_MAP.containsKey(errorCode))
        {
            return new ProxyException(PROXY_EXCEPTION_MAP.get(errorCode), remoteMethodName, errorCode);
        }
        return new GenericHessianException(remoteMethodName, errorCode);
    }
}
