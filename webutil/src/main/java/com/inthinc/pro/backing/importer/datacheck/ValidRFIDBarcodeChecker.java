package com.inthinc.pro.backing.importer.datacheck;


public class ValidRFIDBarcodeChecker extends DataChecker {

    @Override
    public String checkForErrors(String... data) {
        
        String barcode = data[0];
        if (barcode == null || barcode.isEmpty())
            return null;

        if (!dataCache.isValidRFIDBarcode(barcode))
            return "ERROR: RFID Barcode not found in database.";
        
        return null;
    }
}
