package io.github.gungyxy.draft;

/**
 * Created by gung on 10/15/21.
 */
public class TriantaEnaFaceValueConverter implements FaceValueConvertible {
    int[] faceValues={-100000,-1000000,2,3,4,5,6,7,8,9,10,10,10,10};
    public int getFaceValueFromRank(int rank){
        return faceValues[rank];
    }

}
