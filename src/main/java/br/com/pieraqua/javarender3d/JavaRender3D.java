/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.com.pieraqua.javarender3d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

/**
 *
 * @author Paulo
 * 
 * Reference: https://www.alibabacloud.com/blog/construct-a-simple-3d-rendering-engine-with-java_599599
 */
public class JavaRender3D {

    double x[] = {0};
    double y[] = {0};
    private void createAndShowGui()
    {
        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        
        // slider to control horizontal rotation
        JSlider headingSlider = new JSlider(-180, 180, 0);
        //pane.add(headingSlider, BorderLayout.SOUTH);

        // slider to control vertical rotation
        JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
        //pane.add(pitchSlider, BorderLayout.EAST);
        
        
        // panel to display render results
        JPanel renderPanel = new JPanel(){
            
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                double heading = Math.toRadians(x[0]);
                //double heading = Math.toRadians(headingSlider.getValue());
                // XZ rotation
                Matrix3 headingTransform = new Matrix3(new double[] {
                        Math.cos(heading), 0, -Math.sin(heading),
                        0, 1, 0,
                        Math.sin(heading), 0, Math.cos(heading)
                    });
                double pitch = Math.toRadians(y[0]);
                //double pitch = Math.toRadians(pitchSlider.getValue());
                // YZ rotation
                Matrix3 pitchTransform = new Matrix3(new double[] {
                        1, 0, 0,
                        0, Math.cos(pitch), Math.sin(pitch),
                        0, -Math.sin(pitch), Math.cos(pitch)
                    });
                // pitch and heading transform
                Matrix3 transform = headingTransform.multiply(pitchTransform);
                
                // rendering magic
                List<Triangle> tris = new ArrayList<>();

                tris.add(new Triangle(new Vertex(100,100,100),
                                        new Vertex(-100, -100, 100),
                                        new Vertex(-100, 100, -100),
                                        Color.WHITE));
                tris.add(new Triangle(new Vertex(100, 100, 100),
                                      new Vertex(-100, -100, 100),
                                      new Vertex(100, -100, -100),
                                      Color.RED));
                tris.add(new Triangle(new Vertex(-100, 100, -100),
                                      new Vertex(100, -100, -100),
                                      new Vertex(100, 100, 100),
                                      Color.GREEN));
                tris.add(new Triangle(new Vertex(-100, 100, -100),
                                      new Vertex(100, -100, -100),
                                      new Vertex(-100, -100, 100),
                                      Color.BLUE));
                g2.translate(getWidth()/2, getHeight()/2);
                g2.setColor(Color.WHITE);
                for(Triangle t : tris){
                    Vertex v1 = transform.transform(t.v1);
                    Vertex v2 = transform.transform(t.v2);
                    Vertex v3 = transform.transform(t.v3);
                    Path2D path = new Path2D.Double();
                    path.moveTo(v1.x, v1.y);
                    path.lineTo(v2.x, v2.y);
                    path.lineTo(v3.x, v3.y);
                    path.closePath();
                    g2.draw(path);
                }
                
            }
        };
        
        
        renderPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                double yi = 180.0 / renderPanel.getHeight();
                double xi = 180.0 / renderPanel.getWidth();
                x[0] = (int) (e.getX() * xi);
                y[0] = -(int) (e.getY() * yi);
                renderPanel.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        
        
        pane.add(renderPanel, BorderLayout.CENTER);
        
        //headingSlider.addChangeListener(e -> renderPanel.repaint());
        //pitchSlider.addChangeListener(e -> renderPanel.repaint());
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
        JavaRender3D renderer = new JavaRender3D();
        EventQueue.invokeLater(renderer::createAndShowGui);
    }
}
