package documentation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.gridlab.gat.AdaptorInfo;
import org.gridlab.gat.GAT;
import org.gridlab.gat.GATInvocationException;

public class CreateDefaultPropertiesFile {

    public static void main(String[] args) throws GATInvocationException,
            IOException {
        File propertyFile = new File("javagat.properties");
        if (!propertyFile.exists()) {
            propertyFile.createNewFile();
        }
        FileOutputStream out = new FileOutputStream(propertyFile);
        out.write("# JavaGAT properties file\n".getBytes());
        out.write(("# Generated by CreateDefaultPropertiesFile on "
                + new Date(System.currentTimeMillis()) + "\n").getBytes());
        out
                .write("# This file contains all the preferences (published by the adaptors) of javagat with their default values.\n"
                        .getBytes());
        out
                .write("# Uncommenting and/or changing these preferences will have an effect on the default GATContext\n"
                        .getBytes());
        for (String gatObjectType : GAT.getAdaptorTypes()) {
            out
                    .write(("\n################################################\n# Preferences for type: '"
                            + gatObjectType + "'\n################################################\n")
                            .getBytes());
            for (AdaptorInfo adaptorInfo : GAT.getAdaptorInfos(gatObjectType)) {
                out.write(("\n### Preferences for adaptor: '"
                        + adaptorInfo.getShortName() + "'\n").getBytes());
                if (adaptorInfo.getSupportedPreferences() != null) {
                    for (String key : adaptorInfo.getSupportedPreferences()
                            .keySet()) {
                        out.write(("# "
                                + key
                                + "="
                                + adaptorInfo.getSupportedPreferences()
                                        .get(key) + "\n").getBytes());
                    }
                }
            }
        }
    }
}
