package fr.tolc.jahia.intellij.plugin.cnd.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CndPluginUtil {
    
    private static final String PLUGIN_ID = "fr.tolc.jahia.intellij.plugin";
    private static final String CLASSES_FOLDER = "classes";
    private static final String PLUGIN_FOLDER_NAME = ".IntelliJ_Jahia_Plugin";
    
    private static File pluginFolder = getPlugin().getPath();
//    private static File pluginFolder = new File(getPlugin().getPath().getAbsolutePath() + "/IntelliJ_Jahia_Plugin.jar");
    
    private static IdeaPluginDescriptor getPlugin() {
        return PluginManager.getPlugin(PluginId.findId(PLUGIN_ID));
    }

    @NotNull
    public static File getPluginFile(String filePath) {
        if (pluginFolder.getAbsolutePath().endsWith(".jar")) {
            File plugins = pluginFolder.getParentFile();
            File newPluginFolder = new File(plugins.getAbsolutePath() + "/" + PLUGIN_FOLDER_NAME);
            if (newPluginFolder.exists()) {
                try {
                    deleteRecursively(newPluginFolder);
                } catch (IOException e) {
                    //Nothing to do
                }
            }
            extractJarToFolder(pluginFolder, newPluginFolder, "plugin.xml");
            pluginFolder = newPluginFolder;
        }

        File candidateFile = new File(pluginFolder.getAbsolutePath() + "/" + filePath);
        if (candidateFile.exists()) {
            return candidateFile;
        }

        //in case of plugins-sandbox
        return new File(pluginFolder.getAbsolutePath() + "/" + CLASSES_FOLDER + "/" + filePath);
    }

    @Nullable
    public static Path getPluginFilePath(String filePath) {
        File pluginFile = getPluginFile(filePath);
        if (pluginFile.exists()) {
            return Paths.get(pluginFile.getAbsolutePath());
        }
        return null;
    }

    public static void fileToJar(File rootFile, String jarPath, String extensions) throws IOException {
        FileOutputStream fout = new FileOutputStream(jarPath);
        JarOutputStream jarOut = new JarOutputStream(fout);
        addFileToJarRecursive(jarOut, rootFile, rootFile, extensions);
        jarOut.close();
        fout.close();
    }

    private static void addFileToJarRecursive(JarOutputStream jarOut, File file, File rootFile, String extensions) throws IOException {
        if (file.isDirectory()) {
            if (!FileUtil.filesEqual(file, rootFile)) {
                jarOut.putNextEntry(new ZipEntry(getRelativePath(rootFile, file) + "/"));
            }
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    addFileToJarRecursive(jarOut, child, rootFile, extensions);
                }
            }
        } else {
            String entryName;
            if (FileUtil.filesEqual(file, rootFile)) {
                entryName = file.getName();
            } else {
                entryName = getRelativePath(rootFile, file);
            }

            String[] split = entryName.split("\\.");
            if (extensions.contains(split[split.length - 1])) {
                jarOut.putNextEntry(new ZipEntry(entryName));
                jarOut.write(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                jarOut.closeEntry();
            }
        }
    }

    private static String getRelativePath(File parent, File child) {
        return child.getAbsolutePath().substring(parent.getAbsolutePath().length() + 1);
    }
    
    public static void extractJarToFolder(File jar, File destFolder, String... ignoreFiles) {
        JarFile jarFile = null;
        FileOutputStream fos = null;
        InputStream is = null;
        
        try {
            jarFile = new JarFile(jar);

            Enumeration<JarEntry> enumEntries = jarFile.entries();
            while (enumEntries.hasMoreElements()) {
                JarEntry file = enumEntries.nextElement();
                
                boolean skip = false;
                for (String ignoreFile : ignoreFiles) {
                    if (file.getName().endsWith(ignoreFile)) {
                        skip = true;
                        break;
                    }
                }
                if (skip) {
                    continue;
                }
                
                File f = new File(destFolder.getAbsolutePath() + File.separator + file.getName());
                f.getParentFile().mkdirs();
                
                if (file.isDirectory()) {
                    continue;
                }
                
                is = jarFile.getInputStream(file);  // get the input stream
                fos = new FileOutputStream(f);
                while (is.available() > 0) {  // write contents of 'is' to 'fos'
                    fos.write(is.read());
                }
                fos.close();
                is.close();
            }
            
        } catch (Exception e) {
            //Nothing to do
        } finally {
            try {
                if (jarFile != null) {
                    jarFile.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                //Nothing to do
            }
        }
    }
    
    public static void deleteRecursively(File toDelete) throws IOException {
        Files.walkFileTree(Paths.get(toDelete.getAbsolutePath()), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
        toDelete.delete();
    }
}
