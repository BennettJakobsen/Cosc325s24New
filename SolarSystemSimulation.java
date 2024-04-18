package TooneClass;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class SolarSystemSimulation extends JFrame {
    private Timer timer;
    private int dayCounter = 0;
    private JLabel dayLabel;
    private double earthAngle = 0; // Angle of Earth in radians
    private double moonAngle = 0; // Angle of Moon in radians
    private final double earthOrbitRadius = 150; // Radius of Earth's orbit
    private final double moonOrbitRadius = 30; // Radius of Moon's orbit
    private final double earthSpeed = 0.05; // Angular speed of Earth
    private final double moonSpeed = 0.1; // Angular speed of Moon
    private BufferedImage buffer;

    public SolarSystemSimulation() {
        setTitle("Solar System Simulation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create control buttons
        JButton playButton = new JButton("Play");
        JButton pauseButton = new JButton("Pause");
        JButton stopButton = new JButton("Stop");
        dayLabel = new JLabel("Day: 0");

        // Add action listeners to buttons
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });

        // Create control panel for buttons
        JPanel controlPanel = new JPanel();
        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        controlPanel.add(stopButton);
        controlPanel.add(dayLabel);

        getContentPane().add(controlPanel, BorderLayout.NORTH);

        // Initialize timer
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step();
            }
        });

        // Initialize buffer for double buffering
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Create panel for solar system simulation
        JPanel solarSystemPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawSolarSystem(g);
            }
        };
        getContentPane().add(solarSystemPanel, BorderLayout.CENTER);
    }

    public void play() {
        timer.start();
    }

    public void pause() {
        timer.stop();
    }

    public void stop() {
        timer.stop();
        dayCounter = 0;
        dayLabel.setText("Day: 0");
        repaint();
    }

    public void step() {
        dayCounter++;
        dayLabel.setText("Day: " + dayCounter);

        // Update angles for Earth and Moon
        earthAngle += earthSpeed;
        moonAngle += moonSpeed;

        // Repaint the simulation
        repaint();
    }

    // Method to draw the solar system
    private void drawSolarSystem(Graphics g) {
        Graphics2D g2d = (Graphics2D) buffer.getGraphics();
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Get the center of the panel
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Draw Sun
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(centerX - 20, centerY - 20, 40, 40);

        // Calculate Earth's position
        int earthX = (int) (centerX + earthOrbitRadius * Math.cos(earthAngle));
        int earthY = (int) (centerY + earthOrbitRadius * Math.sin(earthAngle));

        // Draw Earth
        g2d.setColor(Color.BLUE);
        g2d.fillOval(earthX - 10, earthY - 10, 20, 20);

        // Calculate Moon's position relative to Earth
        int moonX = earthX + (int) (moonOrbitRadius * Math.cos(moonAngle));
        int moonY = earthY + (int) (moonOrbitRadius * Math.sin(moonAngle));

        // Draw Moon
        g2d.setColor(Color.GRAY);
        g2d.fillOval(moonX - 5, moonY - 5, 10, 10);

        // Draw the buffer to the panel
        g.drawImage(buffer, 0, 0, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SolarSystemSimulation simulation = new SolarSystemSimulation();
                simulation.setVisible(true);
            }
        });
    }
}
