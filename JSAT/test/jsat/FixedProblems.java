package jsat;

import java.util.Random;
import jsat.classifiers.CategoricalData;
import jsat.classifiers.ClassificationDataSet;
import jsat.distributions.multivariate.NormalM;
import jsat.linear.DenseVector;
import jsat.linear.Matrix;
import jsat.linear.Vec;
import jsat.regression.RegressionDataSet;

/**
 * Contains pre determined code for generating specific data sets. 
 * The form and values of the data set are fixed, and do not need 
 * to be specified. Training and testing sets are generated by the
 * same methods.
 * 
 * @author Edward Raff
 */
public class FixedProblems
{
    private static final Vec c2l_m0 = new DenseVector(new double[]{12, 14, 25, 31, 10, 9, 1});
    private static final Vec c2l_m1 = new DenseVector(new double[]{-9, -7, -13, -6, -11, -9, -1});
    private static final NormalM c2l_c0 = new NormalM(c2l_m0, Matrix.eye(c2l_m0.length()));
    private static final NormalM c2l_c1 = new NormalM(c2l_m1, Matrix.eye(c2l_m0.length()));
    
    /**
     * Generates a linearly separable binary classification problem
     * @param dataSetSize the number of points to generate
     * @return a binary classification data set that is linearly separable
     */
    public static ClassificationDataSet get2ClassLinear(int dataSetSize, Random rand)
    {
        ClassificationDataSet train = new ClassificationDataSet(c2l_m0.length(), new CategoricalData[0], new CategoricalData(2));
        
        for(Vec s : c2l_c0.sample(dataSetSize, rand))
            train.addDataPoint(s, new int[0], 0);
        for(Vec s : c2l_c1.sample(dataSetSize, rand))
            train.addDataPoint(s, new int[0], 1);
        
        return train;
    }
    /**
     * Generates a regression problem that can be solved by linear regression methods
     * @param dataSetSize the number of data points to get
     * @param rand the randomness to use
     * @return a regression data set
     */
    public static RegressionDataSet getLinearRegression(int dataSetSize, Random rand)
    {
        RegressionDataSet rds = new RegressionDataSet(c2l_m0.length(), new CategoricalData[0]);
        
        for(Vec s : c2l_c0.sample(dataSetSize, rand))
            rds.addDataPoint(s, new int[0], 1);
        
        return rds;
    }
    
    /**
     * Returns a classification problem with small uniform noise where there is 
     * a small circle of radius 1 within a circle of radius 4. Each circle
     * shares the same center. 
     * 
     * @param dataSetSize the even number of data points to create
     * @param rand the source of randomness
     * @return a classification data set with two classes 
     */
    public static ClassificationDataSet getInnerOuterCircle(int dataSetSize, Random rand)
    {
        ClassificationDataSet train = new ClassificationDataSet(2, new CategoricalData[0], new CategoricalData(2));
        
        int n = dataSetSize/2;
        
        double rInner = 1;
        //iner circle
        for (int i = 0; i < n; i++) 
        {
            double t = 2 * Math.PI * i / n;
            double x = rInner * Math.cos(t) + (rand.nextDouble()-0.5)/4;
            double y = rInner * Math.sin(t) + (rand.nextDouble()-0.5)/4;
            train.addDataPoint(DenseVector.toDenseVec(x, y), new int[0], 0);
        }
        double rOuter = 4;
        //outer cirlce
        for (int i = 0; i < n; i++) 
        {
            double t = 2 * Math.PI * i / n;
            double x = rOuter * Math.cos(t) + (rand.nextDouble()-0.5)/3;
            double y = rOuter * Math.sin(t) + (rand.nextDouble()-0.5)/3;
            train.addDataPoint(DenseVector.toDenseVec(x, y), new int[0], 1);
        }
        
        return train;
    }
}
