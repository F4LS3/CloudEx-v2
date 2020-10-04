package de.f4ls3developer.cloudexv2.cloudapi.handlers;

import de.f4ls3developer.cloudexv2.cloudapi.groups.Group;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class TemplateHandler {

    private File templateDir;

    public TemplateHandler(File templateDir) {
        this.templateDir = templateDir;
    }

    public void copyTemplate(Group group, File target) {
        File sourceDir = new File(this.templateDir.getPath() + "/" + group.getGroupName());

        try {
            Files.copy(sourceDir.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTemporary(File targetDir) {
        File[] files = targetDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteTemporary(file);

                } else {
                    file.delete();
                }
            }
        }

        targetDir.delete();
    }

    public void saveTemplate(Group group, File source) {
        File target = new File(this.templateDir.getPath() + "/" + group.getGroupName());

        try {
            Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTemporaryAndCopyTemplate(Group group, File copyTarget, File deleteTarget) {
        deleteTemporary(deleteTarget);
        copyTemplate(group, copyTarget);
    }

    public boolean templateExists(Group group) {
        return new File(templateDir.getPath() + "/" + group.getGroupName()).exists();
    }
}
