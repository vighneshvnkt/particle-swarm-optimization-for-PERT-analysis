/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algosproject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author vighnesh
 */
public class AlgosProject {

    private static final int TARGET = 100;
    public static final int MAX_INPUTS = 3;
    private static final int MAX_PARTICLES = 20;
    private static final int V_MAX = 10;             // Maximum velocity change allowed.

    private static final int MAX_EPOCHS = 200;
    // The particles will be initialized with data randomly chosen within the range
    // of these starting min and max values: 
    private static final int START_RANGE_MIN = 0;
    private static final int START_RANGE_MAX = 200;
    public static  int epoch = 0;
    public static int gBest = 0;

    public static ArrayList<Particle> particles = new ArrayList<Particle>();
    static MainJFrame mj = new MainJFrame();

    private static void initialize() {
        for (int i = 0; i < MAX_PARTICLES; i++) {
            Particle newParticle = new Particle();
            int total = 0;
            
                newParticle.data(0, getRandomNumber(START_RANGE_MIN, START_RANGE_MAX));
                newParticle.data(2, getRandomNumber(newParticle.data(0), START_RANGE_MAX));
                newParticle.data(1, getRandomNumber(newParticle.data(0), newParticle.data(2)));
                total = newParticle.data(0) + newParticle.data(1) + newParticle.data(2);
             // j
            newParticle.pBest(total);
            particles.add(newParticle);
        } // i
        return;
    }

    private static void PSOAlgorithm() {
        
        int gBestTest = 0;
        Particle aParticle = null;
        
        boolean done = false;

        initialize();

        while (!done) {
            // Two conditions can end this loop:
            //    if the maximum number of epochs allowed has been reached, or,
            //    if the Target value has been found.
            if (epoch < MAX_EPOCHS) {

                for (int i = 0; i < MAX_PARTICLES; i++) {
                    aParticle = particles.get(i);
                      System.out.print("Optimistic time: " +particles.get(i).data(0)+ " ");
               System.out.print("Most likely time: "+particles.get(i).data(1) + " ");
                    System.out.println("Pessimistic time: " +particles.get(i).data(2) + " ");
                System.out.print("PERT Fitness: " +testProblem(i));
             // j
            System.out.print("\n");

                    
                    if (testProblem(i) == TARGET) {
                        done = true;
                    }
                } // i

                gBestTest = minimum();
                aParticle = particles.get(gBest);
                // if(any particle's pBest value is better than the gBest value, make it the new gBest value.
                if (Math.abs(TARGET - testProblem(gBestTest)) < Math.abs(TARGET - testProblem(gBest))) {
                    gBest = gBestTest;
                }
                displayParticlesJFreeChart();
                getVelocity(gBest);

                updateparticles(gBest);

                System.out.println("epoch number: " + epoch);

                epoch += 1;

            } else {
                done = true;
            }
        }
        return;
    }

    private static void getVelocity(int gBestindex) {
        //  from Kennedy & Eberhart(1995).
        //    vx[][] = vx[][] + 2 * rand() * (pbestx[][] - presentx[][]) + 
        //                      2 * rand() * (pbestx[][gbest] - presentx[][])

        int testResults = 0;
        int bestResults = 0;
        double vValue = 0.0;
        Particle aParticle = null;

        bestResults = testProblem(gBestindex);

        for (int i = 0; i < MAX_PARTICLES; i++) {
            testResults = testProblem(i);
            aParticle = particles.get(i);
            vValue = aParticle.velocity() + 2 * new Random().nextDouble() * (aParticle.pBest() - testResults) + 2 * new Random().nextDouble() * (bestResults - testResults);

            if (vValue > V_MAX) {
                aParticle.velocity(V_MAX);
            } else if (vValue < -V_MAX) {
                aParticle.velocity(-V_MAX);
            } else {
                aParticle.velocity(vValue);
            }
        }
        return;
    }

    private static void updateparticles(int gBestindex) {
        Particle gBParticle = particles.get(gBestindex);

        for (int i = 0; i < MAX_PARTICLES; i++) {
            for (int j = 0; j < MAX_INPUTS; j++) {
                if (particles.get(i).data(j) != gBParticle.data(j)) {
                    particles.get(i).data(j, particles.get(i).data(j) + (int) Math.round(particles.get(i).velocity()));
                }
            } // j

            // Check pBest value.
            int total = testProblem(i);
            if (Math.abs(TARGET - total) < particles.get(i).pBest()) {
                particles.get(i).pBest(total);
            }

        } // i
        return;
    }

    private static int testProblem(int index) {
        int total = 0;
        Particle aParticle = null;

        aParticle = particles.get(index);

        total = (aParticle.data(0) + 4 * aParticle.data(1) + aParticle.data(2)) / 6;
        return total;
    }

    private static void printSolution() {
        // Find solution particle.
        int i=0;
        Particle particle = null;
        for (i = 0; i < particles.size(); i++) {
            particle = particles.get(i);
            if (testProblem(i) == TARGET) {
                break;
            }
        }
            // Print it.
            System.out.println("Particle " + i + " has achieved target.");
 System.out.println("PERT " + particle.mpBest);
            
               
                    System.out.print("Optimistic time: " +particle.data(0)+ " ");
               System.out.print("Most likely time: "+particle.data(1) + " ");
                    System.out.println("Pessimistic time: " +particle.data(2) + " ");
                
             // j
            System.out.print("\n");
            return;
        
    }

    private static int getRandomNumber(int low, int high) {
        return (int) ((high - low) * new Random().nextDouble() + low);
    }

    private static int minimum() {
        // Returns an array index.
        int winner = 0;
        boolean foundNewWinner = false;
        boolean done = false;

        while (!done) {
            foundNewWinner = false;
            for (int i = 0; i < MAX_PARTICLES; i++) {
                if (i != winner) {             // Avoid self-comparison.
                    // The minimum has to be in relation to the Target.
                    if (Math.abs(TARGET -testProblem(i)) < Math.abs(TARGET -testProblem(winner))) {
                        if (particles.get(i).data(0) <= particles.get(i).data(1) && particles.get(i).data(1) <= particles.get(i).data(2)
                                && particles.get(i).data(0) <= particles.get(i).data(2)
                                ) {
                            winner = i;
                            foundNewWinner = true;
                            System.out.println("Winner is " +winner);
                        }
                    }

                }
            }

            if (foundNewWinner == false) {
                done = true;
            }
        }
            
        return winner;
    }

  

    public static void main(String[] args) {
        PSOAlgorithm();
        printSolution();
        return;
    }
    
   public static void displayParticlesJFreeChart() {
        
        try {
            SwingUtilities.invokeAndWait(
                    
                    new ParticleThread()
                    
            );        
        } catch (InterruptedException ex) {
            Logger.getLogger(AlgosProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(AlgosProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        long start = System.currentTimeMillis();
        long end = 0;
        while(true){
            end = System.currentTimeMillis();
            if((end-start)>1000)break;
        }
        
    }

}
