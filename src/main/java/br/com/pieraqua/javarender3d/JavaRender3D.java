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
import java.awt.geom.Path2D;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Paulo
 */
public class JavaRender3D {

    private void createAndShowGui()
    {
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        
        // panel to display render results
        JPanel renderPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
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
                    Path2D path = new Path2D.Double();
                    path.moveTo(t.v1.x, t.v1.y);
                    path.lineTo(t.v2.x, t.v2.y);
                    path.lineTo(t.v3.x, t.v3.y);
                    path.closePath();
                    g2.draw(path);
                }
                
            }
        };
        
        pane.add(renderPanel, BorderLayout.CENTER);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
        JavaRender3D renderer = new JavaRender3D();
        EventQueue.invokeLater(renderer::createAndShowGui);
    }
}
