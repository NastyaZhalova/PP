package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Snake extends JPanel implements ActionListener {
    private final int SIZE = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int DELAY = 150;

    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private int appleX;
    private int appleY;

    private boolean inGame = true;
    private Timer timer;

    private enum Direction { LEFT, RIGHT, UP, DOWN }
    private Direction direction = Direction.RIGHT;

    public Snake() {
        setBackground(Color.black);
        setPreferredSize(new Dimension(SIZE, SIZE));
        initGame();

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT && direction != Direction.RIGHT) {
                    direction = Direction.LEFT;
                }
                if (key == KeyEvent.VK_RIGHT && direction != Direction.LEFT) {
                    direction = Direction.RIGHT;
                }
                if (key == KeyEvent.VK_UP && direction != Direction.DOWN) {
                    direction = Direction.UP;
                }
                if (key == KeyEvent.VK_DOWN && direction != Direction.UP) {
                    direction = Direction.DOWN;
                }
            }
        });
        setFocusable(true);
    }

    private void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 50 - i * DOT_SIZE;
            y[i] = 50;
        }
        locateApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, DOT_SIZE, DOT_SIZE);
            for (int i = 0; i < dots; i++) {
                g.setColor(i == 0 ? Color.green : Color.white);
                g.fillRect(x[i], y[i], DOT_SIZE, DOT_SIZE);
            }
        } else {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Game Over", SIZE / 2 - 50, SIZE / 2);
        }
    }

    private void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            locateApple();
        }
    }

    private void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case LEFT -> x[0] -= DOT_SIZE;
            case RIGHT -> x[0] += DOT_SIZE;
            case UP -> y[0] -= DOT_SIZE;
            case DOWN -> y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) inGame = false;
        }
        if (x[0] >= SIZE || x[0] < 0 || y[0] >= SIZE || y[0] < 0) inGame = false;
        if (!inGame) timer.stop();
    }

    private void locateApple() {
        appleX = new Random().nextInt(SIZE / DOT_SIZE) * DOT_SIZE;
        appleY = new Random().nextInt(SIZE / DOT_SIZE) * DOT_SIZE;
    }

    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Змейка");
        Snake game = new Snake();
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        game.requestFocusInWindow();
    }
}
