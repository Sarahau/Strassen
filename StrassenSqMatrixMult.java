import java.util.Vector;

public class StrassenSqMatrixMult {

    public static void main(String[] args) {
        Vector<Vector<Double>> matrixA = new Vector<>();
        Vector<Vector<Double>> matrixB = new Vector<>();

        int n = 16;

        for (int i = 0; i < n; i++) {
            Vector<Double> tempRow = new Vector<>();
            for (int j = 0; j < n; j++) {
                Double tempDouble = (i + j + 0.0);
                tempRow.addElement(tempDouble);
            }
            matrixA.addElement(tempRow);
        }

        for (int i = 0; i < n; i++) {
            Vector<Double> tempRow = new Vector<>();
            for (int j = 0; j < n; j++) {
                Double tempDouble = (i * j * 1.0);
                tempRow.addElement(tempDouble);
            }
            matrixB.addElement(tempRow);
        }

        System.out.println(matrixA);
        System.out.println(matrixB);
        Vector<Vector<Double>> matrixC = strassenSqMatrixMultRecursive(matrixA, matrixB);
        System.out.println(matrixC);
    }

    private static Vector<Vector<Double>> strassenSqMatrixMultRecursive(Vector<Vector<Double>> a, Vector<Vector<Double>> b) {
        int n = a.size();
        Vector<Vector<Double>> c = new Vector<>();

        for (int i = 0; i < n; i++) {
            Vector<Double> tempRow = new Vector<>();
            for (int j = 0; j < n; j++) {
                tempRow.addElement(0.0);
            }
            c.addElement(tempRow);
        }
        if (n == 1) {
            c.get(0).set(0, a.get(0).get(0) * b.get(0).get(0));
        } else {
            Vector<Vector<Double>> a11 = new Vector<>();
            Vector<Vector<Double>> a12 = new Vector<>();
            Vector<Vector<Double>> a21 = new Vector<>();
            Vector<Vector<Double>> a22 = new Vector<>();

            Vector<Vector<Double>> b11 = new Vector<>();
            Vector<Vector<Double>> b12 = new Vector<>();
            Vector<Vector<Double>> b21 = new Vector<>();
            Vector<Vector<Double>> b22 = new Vector<>();

            int m = n / 2;

            addToMatrix(a11, a, 0, m, 0, m);
            addToMatrix(a12, a, 0, m, m, n);
            addToMatrix(a21, a, m, n, 0, m);
            addToMatrix(a22, a, m, n, m, n);

            addToMatrix(b11, b, 0, m, 0, m);
            addToMatrix(b12, b, 0, m, m, n);
            addToMatrix(b21, b, m, n, 0, m);
            addToMatrix(b22, b, m, n, m, n);

            Vector<Vector<Double>> s1 = subMatrix(b12, b22);
            Vector<Vector<Double>> s2 = addMatrix(a11, a12);
            Vector<Vector<Double>> s3 = addMatrix(a21, a22);
            Vector<Vector<Double>> s4 = subMatrix(b21, b11);
            Vector<Vector<Double>> s5 = addMatrix(a11, a22);
            Vector<Vector<Double>> s6 = addMatrix(b11, b22);
            Vector<Vector<Double>> s7 = subMatrix(a12, a22);
            Vector<Vector<Double>> s8 = addMatrix(b21, b22);
            Vector<Vector<Double>> s9 = subMatrix(a11, a21);
            Vector<Vector<Double>> s10 = addMatrix(b11, b12);

            Vector<Vector<Double>> p1 = strassenSqMatrixMultRecursive(a11, s1);
            Vector<Vector<Double>> p2 = strassenSqMatrixMultRecursive(s2, b22);
            Vector<Vector<Double>> p3 = strassenSqMatrixMultRecursive(s3, b11);
            Vector<Vector<Double>> p4 = strassenSqMatrixMultRecursive(a22, s4);
            Vector<Vector<Double>> p5 = strassenSqMatrixMultRecursive(s5, s6);
            Vector<Vector<Double>> p6 = strassenSqMatrixMultRecursive(s7, s8);
            Vector<Vector<Double>> p7 = strassenSqMatrixMultRecursive(s9, s10);

            Vector<Vector<Double>> c11 = subMatrix(addMatrix(addMatrix(p5, p4), p6), p2);
            Vector<Vector<Double>> c12 = addMatrix(p1, p2);
            Vector<Vector<Double>> c21 = addMatrix(p3, p4);
            Vector<Vector<Double>> c22 = subMatrix(subMatrix(addMatrix(p5, p1), p3), p7);

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    c.get(i).set(j, c11.get(i).get(j));
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = m; j < n; j++) {
                    c.get(i).set(j, c12.get(i).get(j - m));
                }
            }
            for (int i = m; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    c.get(i).set(j, c21.get(i - m).get(j));
                }
            }
            for (int i = m; i < n; i++) {
                for (int j = m; j < n; j++) {
                    c.get(i).set(j, c22.get(i - m).get(j - m));
                }
            }
        }
        return c;
    }

    private static void addToMatrix(Vector<Vector<Double>> result, Vector<Vector<Double>> source, int startingI, int rangeI, int startingJ, int rangeJ) {
        for (int i = startingI; i < rangeI; i++) {
            Vector<Double> tempRow = new Vector<>();
            for (int j = startingJ; j < rangeJ; j++) {
                tempRow.addElement(source.get(i).get(j));
            }
            result.addElement(tempRow);
        }
    }

    private static Vector<Vector<Double>> addMatrix(Vector<Vector<Double>> a, Vector<Vector<Double>> b) {
        Vector<Vector<Double>> sum = new Vector<>();
        int n = a.size();
        for (int i = 0; i < n; i++) {
            Vector<Double> tempRow = new Vector<>();
            for (int j = 0; j < n; j++) {
                tempRow.addElement(a.get(i).get(j) + b.get(i).get(j));
            }
            sum.addElement(tempRow);
        }
        return sum;
    }

    private static Vector<Vector<Double>> subMatrix(Vector<Vector<Double>> a, Vector<Vector<Double>> b) {
        Vector<Vector<Double>> difference = new Vector<>();
        int n = a.size();
        for (int i = 0; i < n; i++) {
            Vector<Double> tempRow = new Vector<>();
            for (int j = 0; j < n; j++) {
                tempRow.addElement(a.get(i).get(j) - b.get(i).get(j));
            }
            difference.addElement(tempRow);
        }
        return difference;
    }
}
