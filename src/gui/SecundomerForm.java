package gui;

import stopwatch.StopWatch;
import stopwatch.TimerData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Mykhailo on 18.09.2016.
 */
public class SecundomerForm extends JFrame {
    public static final String PAUSE = "Pause";
    public static final String RESUME = "Resume";
    public static final String START = "Start";
    public static final Dimension START_SIZE = new Dimension(200, 50);
    public static final Dimension PAUSE_AND_RESUME_SIZE = new Dimension(100, 50);

    private JPanel rootPanel;
    private JButton startAndPauseButton;
    private JButton stopButton;
    private JLabel timer;

    private StopWatch stopWatch;

    public SecundomerForm() {

        setContentPane(rootPanel);
        setTitle("Секундомер");
        Dimension size = new Dimension(400, 300);
        setSize(new Dimension(size));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        decorateStopButton();
        decorateStartButton();

        startAndPauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stopWatch == null) {
                    stopWatch = new StopWatch(new TimerInvoker());
                    stopWatch.start();
                    setActiveState();
                } else if (stopWatch.isPaused()) {
                    stopWatch.setIsPaused(false);
                    setActiveState();
                } else {
                    stopWatch.setIsPaused(true);
                    setPausedState();
                }
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stopWatch != null) {
                    stopWatch.interrupt();
                }
            }
        });
    }

    public void setTime(long time) {
        long millis = time % 1000;
        time /= 1000;
        long sec = time % 60;
        time /= 60;
        long mins = time % 60;
        time /= 60;
        long hours = time % 100;
        timer.setText(String.format("%02d:%02d:%02d.%03d", hours, mins, sec, millis));
    }

    public long getTime() {
        String text[] = timer.getText().split("[:\\.]");
        long time = Integer.parseInt(text[0]) * 60;
        time += Integer.parseInt(text[1]);
        time *= 60;
        time += Integer.parseInt(text[2]);
        time *= 1000;
        time += Integer.parseInt(text[3]);
        return time;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private class TimerInvoker implements TimerData {

        @Override
        public void setTime(long time) {
            SwingUtilities.invokeLater(() -> SecundomerForm.this.setTime(time));
        }

        @Override
        public long getTime() {
            long time[] = new long[1];
            try {
                SwingUtilities.invokeAndWait(() -> time[0] = SecundomerForm.this.getTime());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return time[0];
        }

        @Override
        public void stop() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    stopWatch = null;
                    setStoppedState();
                }
            });

        }
    }

    private void setStoppedState() {
        stopButton.setVisible(false);
        startAndPauseButton.setText(START);
        startAndPauseButton.setPreferredSize(START_SIZE);
        setTime(0);
    }

    private void setPausedState() {
        startAndPauseButton.setText(RESUME);
    }

    private void setActiveState() {
        startAndPauseButton.setText(PAUSE);
        startAndPauseButton.setPreferredSize(PAUSE_AND_RESUME_SIZE);
        stopButton.setVisible(true);
    }

    private void decorateStopButton() {
        stopButton.setContentAreaFilled(false);
        stopButton.setOpaque(true);
        stopButton.setVisible(false);
    }

    private void decorateStartButton() {
        startAndPauseButton.setContentAreaFilled(false);
        startAndPauseButton.setOpaque(true);
    }

}
