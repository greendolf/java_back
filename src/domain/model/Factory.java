package domain.model;

import domain.ICalculator;

import java.util.Map;

public class Factory {
    public static ICalculator createCalculator(Map<String, Double> values) {
        int Nx = values.get("Nx").intValue();
        int Ny = values.get("Ny").intValue();
        double time_end = values.get("time_end");
        double length = values.get("length");
        double width = values.get("width");
        double lambda = values.get("lambda");
        double ro = values.get("ro");
        double C = values.get("C");
        double T_start = values.get("T_start");
        double T_left = values.get("T_left");
        double T_right = values.get("T_right");
        return new Model(Nx, Ny, time_end, length, width, lambda, ro, C, T_left, T_right, T_start);
    }
}

class Model implements ICalculator {
    int Nx, Ny;
    double length, width;
    double time_end;
    double lambda, ro, C;
    double T_start, T_left, T_right;

    protected Model(int Nx, int Ny, double time_end, double length, double width, double lambda, double ro, double C, double T_left, double T_right, double T_start) {
        this.Nx = Nx;
        this.Ny = Ny;
        this.length = length;
        this.width = width;
        this.time_end = time_end;
        this.lambda = lambda;
        this.ro = ro;
        this.C = C;
        this.T_start = T_start;
        this.T_left = T_left;
        this.T_right = T_right;
    }

    public double[][] calculate() {
        int mf = 102;
        double[][] T = new double[mf][mf];
        double[] alfa = new double[mf];
        double[] beta = new double[mf];
        double ai, bi, ci, fi;

        // Determine the calculation steps of the grid in spatial coordinates
        double hx = length / (Nx - 1);
        double hy = width / (Ny - 1);

        // Determine the thermal diffusivity
        double a = lambda / (ro * C);

        // Determine the calculation step of the grid in time
        double tau = time_end / 100.0;

        // Determine the temperature field at the initial time
        for (int i = 0; i < Nx; i++) {
            for (int j = 0; j < Ny; j++) {
                T[i][j] = T_start;
            }
        }

        // Perform integration of the unsteady heat conduction equation
        double time = 0;
        while (time < time_end) {
            // Increase the time variable by the step τ
            time += tau;

            // Solve the system of linear equations in the direction of the x-axis to determine the temperature field at the intermediate time layer
            for (int j = 0; j < Ny; j++) {
                // Determine the initial sweep coefficients based on the left boundary condition
                alfa[0] = 0.0;
                beta[0] = T_left;

                // Loop with a parameter to determine the sweep coefficients according to formula (8)
                for (int i = 1; i < Nx - 1; i++) {
                    ai = lambda / (hx * hx);
                    bi = 2.0 * lambda / (hx * hx) + ro * C / tau;
                    ci = lambda / (hx * hx);
                    fi = -ro * C * T[i][j] / tau;
                    alfa[i] = ai / (bi - ci * alfa[i - 1]);
                    beta[i] = (ci * beta[i - 1] - fi) / (bi - ci * alfa[i - 1]);
                }

                // Determine the temperature value at the right boundary based on the right boundary condition
                T[Nx - 1][j] = T_right;

                // Using the relationship (7), determine the unknown temperature field at the intermediate time layer
                for (int i = Nx - 2; i >= 0; i--) {
                    T[i][j] = alfa[i] * T[i + 1][j] + beta[i];
                }
            }

            // Solve the system of linear equations in the direction of the y-axis to determine the temperature field at the entire time layer
            for (int i = 1; i < Nx - 1; i++) {
                // Determine the initial sweep coefficients based on the lower boundary condition, using the relationships (20) under the condition that q1 = 0
                alfa[0] = 2.0 * a * tau / (2.0 * a * tau + hy * hy);
                beta[0] = hy * hy * T[i][0] / (2.0 * a * tau + hy * hy);

                // Loop with a parameter to determine the sweep coefficients according to formula (8)
                for (int j = 1; j < Ny - 1; j++) {
                    ai = lambda / (hy * hy);
                    bi = 2.0 * lambda / (hy * hy) + ro * C / tau;
                    ci = lambda / (hy * hy);
                    fi = -ro * C * T[i][j] / tau;

                    alfa[j] = ai / (bi - ci * alfa[j - 1]);
                    beta[j] = (ci * beta[j - 1] - fi) / (bi - ci * alfa[j - 1]);
                }

                // Determine the temperature value at the upper boundary, using the relationship (21) under the condition that q2 = 0
                T[i][Ny - 1] = (2.0 * a * tau * beta[Ny - 2] + hy * hy * T[i][Ny - 1]) / (2.0 * a * tau * (1.0 - alfa[Ny - 2]) + hy * hy);

                // Using the relationship (7), determine the unknown temperature field at the intermediate time layer
                for (int j = Ny - 2; j >= 0; j--) {
                    T[i][j] = alfa[j] * T[i][j + 1] + beta[j];
                }
            }
        }
        return T;
    }
}