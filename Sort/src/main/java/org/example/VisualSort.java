package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class VisualSort extends JFrame {
    private BarsPanel barsPanel;
    private final JButton shuffleButton;
    private final JButton sortButton;
    public final JSlider speedSlider;
    private final JSpinner countSpinner;

    private int[] data;
    private volatile boolean sorting = false;

    public VisualSort() {
        super("Визуальная сортировка (Bubble Sort)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        int n = 50;
        data = generateData(n);

        barsPanel = new BarsPanel(data);

        shuffleButton = new JButton(new AbstractAction("Перемешать") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sorting) return;
                int count = (Integer) countSpinner.getValue();
                data = generateData(count);
                barsPanel.setData(data);
            }
        });

        sortButton = new JButton(new AbstractAction("Сортировать") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sorting) return;
                startBubbleSort();
            }
        });

        speedSlider = new JSlider(0, 100, 30);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setMajorTickSpacing(25);
        speedSlider.setMinorTickSpacing(5);

        countSpinner = new JSpinner(new SpinnerNumberModel(n, 5, 200, 5));
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(new JLabel("Количество столбиков:"));
        controls.add(countSpinner);
        controls.add(new JLabel("Скорость:"));
        controls.add(speedSlider);
        controls.add(shuffleButton);
        controls.add(sortButton);

        setLayout(new BorderLayout());
        add(controls, BorderLayout.NORTH);
        add(barsPanel, BorderLayout.CENTER);
    }

    public int[] generateData(int n) {
        Random rnd = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = 10 + rnd.nextInt(91);
        }
        return arr;
    }

    private void startBubbleSort() {
        sorting = true;
        sortButton.setEnabled(false);
        shuffleButton.setEnabled(false);

        SwingWorker<Void, int[]> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                bubbleSortAnimated(data);
                return null;
            }

            @Override
            protected void done() {
                sorting = false;
                sortButton.setEnabled(true);
                shuffleButton.setEnabled(true);
            }
        };
        worker.execute();
    }

    private void bubbleSortAnimated(int[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                barsPanel.setHighlight(j, j + 1);

                if (arr[j] > arr[j + 1]) {
                    int t = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = t;
                    swapped = true;
                }

                barsPanel.repaint();
                sleepBySpeed();
            }
            barsPanel.setSortedTailLength(i + 1);
            barsPanel.clearHighlight();
            barsPanel.repaint();
            sleepBySpeed();

            if (!swapped) break;
        }
        barsPanel.clearHighlight();
        barsPanel.setSortedTailLength(n);
        barsPanel.repaint();
    }

    public void sleepBySpeed() {
        int speed = speedSlider.getValue();
        int delayMs = Math.max(2, 120 - speed);
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException ignored) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VisualSort().setVisible(true));
    }

    public static class BarsPanel extends JPanel {
        private int[] data;
        private Integer hiA = null, hiB = null;
        private int sortedTail = 0;

        public BarsPanel(int[] data) {
            this.data = data;
            setBackground(Color.WHITE);
        }

        public void setData(int[] data) {
            this.data = data;
            this.sortedTail = 0;
            repaint();
        }

        public void setHighlight(Integer a, Integer b) {
            this.hiA = a;
            this.hiB = b;
        }

        public void clearHighlight() {
            this.hiA = null;
            this.hiB = null;
        }

        public void setSortedTailLength(int len) {
            this.sortedTail = Math.max(0, Math.min(len, data.length));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (data == null || data.length == 0) return;

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int n = data.length;

            int gap = 2;
            int barWidth = Math.max(1, (w - (n + 1) * gap) / n);
            int maxVal = 100;

            for (int i = 0; i < n; i++) {
                int val = data[i];
                int barHeight = (int) ((val / (double) maxVal) * (h - 20));
                int x = gap + i * (barWidth + gap);
                int y = h - barHeight;

                Color color;
                boolean isHighlighted = (hiA != null && hiB != null && (i == hiA || i == hiB));
                boolean inSortedTail = (i >= n - sortedTail);

                if (isHighlighted) {
                    color = new Color(255, 140, 0);
                } else if (inSortedTail) {
                    color = new Color(46, 204, 113);
                } else {
                    color = new Color(52, 152, 219);
                }

                g2.setColor(color);
                g2.fillRect(x, y, barWidth, barHeight);

                g2.setColor(new Color(0, 0, 0, 40));
                g2.drawRect(x, y, barWidth, barHeight);
            }

            g2.dispose();
        }
    }
}
