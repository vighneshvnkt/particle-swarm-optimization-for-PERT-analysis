/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algosproject;

import java.awt.Color;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author vighnesh
 */
public class ParticleThread implements Runnable{

    @Override
    public void run() {
        
        // Get object from InvestmentPSO class
        ArrayList<Particle> particles = AlgosProject.particles;
        MainJFrame mj = AlgosProject.mj;
        
        
               
        // Create dataset                    
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series1 = new XYSeries("Optimistic values ");
        XYSeries series4 = new XYSeries("Optimistic gBest ");
        XYSeries series2 = new XYSeries("Most likely values");
        XYSeries series5 = new XYSeries("Most likely gBest ");
        XYSeries series3 = new XYSeries("Pessimistic values");
        XYSeries series6 = new XYSeries("Pessimistic gBest ");
        
        
    
        for(Particle p : particles){
            
            series1.add(p.data(0), 1);
            series2.add(p.data(1), 3);
            series3.add(p.data(2),5);
            series4.add(AlgosProject.particles.get(AlgosProject.gBest).data(0),2);
            series5.add(AlgosProject.particles.get(AlgosProject.gBest).data(1),4);
            series6.add(AlgosProject.particles.get(AlgosProject.gBest).data(2),6);

                
                        
        }

        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);
        dataset.addSeries(series5);
        dataset.addSeries(series6);
        XYDataset xydataset = dataset;
                        
                        
                            
        // Create chart
        JFreeChart chart = ChartFactory.createScatterPlot(
        "Optimistic, most likely and pessimistic time values", 
        "Time", "Type", xydataset);
        
        

    
        //Changes background color
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(255,228,196));
        
        
        

        
        
                  
   
        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        mj.getContentPane().removeAll();
        mj.setContentPane(panel);                        
                        
        mj.setVisible(true);        
        
        
    }
    
}
