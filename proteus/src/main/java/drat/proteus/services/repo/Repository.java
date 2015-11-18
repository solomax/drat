package drat.proteus.services.repo;

import java.io.File;

public class Repository {
    public class Size {
        public int numberOfFiles;
        public long memorySize;
        public Size(int fileSize, long memSize) {
            this.numberOfFiles = fileSize;
            this.memorySize = memSize;
        }
    }
    private static final int SIZE_UNKNOWN = -1; //sentinel value before repo size computation
    private String dir;
    private int numberOfFiles = SIZE_UNKNOWN;
    private long memorySize = SIZE_UNKNOWN;

    public Repository(String dirPath) {
        this.dir = dirPath;
    }

    public Size getSize() {
        if (numberOfFiles == SIZE_UNKNOWN) {
            numberOfFiles = 0;
            memorySize = 0;
            getRepositoryFileInformation(this.dir);
        }
        return new Size(this.numberOfFiles, this.memorySize);
    }

    public void reset() {
        numberOfFiles = SIZE_UNKNOWN;
        memorySize = SIZE_UNKNOWN;
    }


    private void getRepositoryFileInformation(String dirPath) {
        File f = new File(dirPath);
        File[] files = f.listFiles();
        if (files != null) {
            for (File file: files) {
                if(file.isFile()) {
                    numberOfFiles++;
                    memorySize += file.length();
                }
                if (file.isDirectory()) {
                    getRepositoryFileInformation(file.getAbsolutePath());
                }
            }
        }
    }
}
