package com.inthinc.pro.backing;

import java.io.IOException;
import java.io.OutputStream;

public interface ServableFileObject {
    /**
     * Return the filename without extension.
     * @return the filename without extension.
     */
    public String getFileName();

    /**
     * Return the filename with the extension.
     * @return the filename with the extension.
     */
    public String getFileNamePlusExtension();

    /**
     * Return the FormatType.  FormatType includes suffix, label, and contentType.
     * @return the FormatType.
     */
    public FormatType getFormatType();

    /**
     * Writes this object to the provided OutputStream.  
     *
     * @param out
     * @throws IOException
     */
    public void write(OutputStream out) throws IOException;

    public enum FormatType {
        PDF(".pdf", "pdf document", "application/pdf"),
        EXCEL(".xls", "Excel Document", "application/xls"),
        HTML(".html", "HTML Document", ""),
        CSV(".csv", "CSV Document", ""),
        CRASHTRACE(".bin", "Waysmart Crash Trace", "application/recon");

        private String suffix;
        private String label;
        private String contentType;

        private FormatType(String suffix, String label, String contentType) {
            this.suffix = suffix;
            this.label = label;
            this.contentType = contentType;
        }

        public String getSuffix() {
            return suffix;
        }

        public String getLabel() {
            return label;
        }

        public String getContentType() {
            return contentType;
        }
    }
}