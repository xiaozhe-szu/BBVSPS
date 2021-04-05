package org.fisco.bcos.cloud.schnorr;

import java.io.File;

public class GetProjectPath {
    public static String getPath() {
        File directory = new File("");
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath().replace("\\", "/");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return courseFile;
    }

}
