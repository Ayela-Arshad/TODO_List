//package com.mycompany.todolist;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;

class Task extends JPanel {

    JLabel index;
    JTextField taskName;
    JButton done;

    private boolean checked;

    Task() {
        this.setPreferredSize(new Dimension(400, 20)); 
        this.setBackground(Color.lightGray); 

        this.setLayout(new BorderLayout());

        checked = false;

        index = new JLabel(""); 
        index.setPreferredSize(new Dimension(20, 20)); 
        index.setHorizontalAlignment(JLabel.CENTER); 
        this.add(index, BorderLayout.WEST); 

        taskName = new JTextField("Write something.."); 
        taskName.setBorder(BorderFactory.createEmptyBorder()); 
        taskName.setBackground(Color.lightGray); 

        this.add(taskName, BorderLayout.CENTER);

        done = new JButton("Completed");
        done.setPreferredSize(new Dimension(80, 20));
        done.setBorder(BorderFactory.createEmptyBorder());
        done.setBackground(Color.RED);
        done.setFocusPainted(false);

        this.add(done, BorderLayout.EAST);

    }

    public void changeIndex(int num) {
        this.index.setText(num + ""); 
        this.revalidate(); 
    }

    public JButton getDone() {
        return done;
    }

    public boolean getState() {
        return checked;
    }

    public void changeState() {
        this.setBackground(Color.GREEN);
        taskName.setBackground(Color.GREEN);
        checked = true;
        revalidate();
    }
}

class List extends JPanel {

    Color lightColor = new Color(252, 221, 176);

    List() {

        GridLayout layout = new GridLayout(10, 1);
        layout.setVgap(5); 

        this.setLayout(layout); 
        this.setPreferredSize(new Dimension(400, 560));
        this.setBackground(Color.WHITE);
    }

    public void updateNumbers() {
        Component[] listItems = this.getComponents();

        for (int i = 0; i < listItems.length; i++) {
            if (listItems[i] instanceof Task) {
                ((Task) listItems[i]).changeIndex(i + 1);
            }
        }

    }

    public void removeCompletedTasks() {

        for (Component c : getComponents()) {
            if (c instanceof Task) {
                if (((Task) c).getState()) {
                    remove(c); // remove the component
                    updateNumbers(); // update the indexing of all items
                }
            }
        }

    }
}

class Footer extends JPanel {

    JButton addTask;
    JButton clear;

    Border emptyBorder = BorderFactory.createEmptyBorder();

    Footer() {
        this.setPreferredSize(new Dimension(400, 60));
        this.setBackground(Color.WHITE);
        addTask = new JButton("Add Task"); 
        addTask.setBorder(emptyBorder);
        addTask.setFont(new Font("Times New Roman", Font.BOLD, 24)); 
        addTask.setVerticalAlignment(JButton.BOTTOM); 
        addTask.setBackground(Color.WHITE); 
        this.add(addTask); 

        this.add(Box.createHorizontalStrut(20)); 

        clear = new JButton("Clear finished tasks"); 
        clear.setFont(new Font("Times New Roman", Font.BOLD, 24));
        clear.setBorder(emptyBorder); 
        clear.setBackground(Color.WHITE); 
        this.add(clear); 
    }

    public JButton getNewTask() {
        return addTask;
    }

    public JButton getClear() {
        return clear;
    }
}

class TitleBar extends JPanel {

    TitleBar() {
        this.setPreferredSize(new Dimension(400, 80)); 
        this.setBackground(Color.WHITE); 
        JLabel titleText = new JLabel("WorkIt: My To-Do List"); 
        titleText.setPreferredSize(new Dimension(300, 100)); 
        titleText.setFont(new Font("Times New Roman", Font.ITALIC, 30)); 
        titleText.setHorizontalAlignment(JLabel.CENTER);
        this.add(titleText); 
    }
}

class AppFrame extends JFrame {

    private TitleBar title;
    private Footer footer;
    private List list;

    private JButton newTask;
    private JButton clear;

    AppFrame() {
        this.setSize(400, 600); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setVisible(true); 

        title = new TitleBar();
        footer = new Footer();
        list = new List();

        this.add(title, BorderLayout.NORTH); 
        this.add(footer, BorderLayout.SOUTH); 
        this.add(list, BorderLayout.CENTER); 

        newTask = footer.getNewTask();
        clear = footer.getClear();

        addListeners();
    }

    public void addListeners() {
        newTask.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Task task = new Task();
                list.add(task); // Add new task to list
                list.updateNumbers(); // Updates the numbers of the tasks

                task.getDone().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {

                        task.changeState(); // Change color of task
                        list.updateNumbers(); // Updates the numbers of the tasks
                        revalidate(); // Updates the frame

                    }
                });
            }

        });

        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                list.removeCompletedTasks(); 
                repaint(); 
            }
        });
    }

}

public class ToDoList {

    public static void main(String args[]) {
        AppFrame frame = new AppFrame(); 
    }
}

