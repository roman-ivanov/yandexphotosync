/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.bizon.sunc.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import ua.pp.bizon.sunc.api.Path;
import ua.pp.bizon.sunc.api.PathFactory;
import ua.pp.bizon.sunc.api.impl.PathNotSupportedException;
import ua.pp.bizon.sunc.remote.impl.RemoteException;
import ua.pp.bizon.sunc.ui.FileFrameBean.PathUpdatedEvent;

/**
 * 
 * @author roman
 */
public class FilePanel extends javax.swing.JPanel implements PathUpdatedEvent {

    private static final long serialVersionUID = -7914312024396463904L;
    private FileFrameBean bean;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ErrorHandler handler;

    @Autowired
    @Qualifier(value = "pathFactoryUI")
    private PathFactory factory;

    public void setBean(FileFrameBean bean) {
        if (this.bean != null) {
            this.bean.removePathUpdatedEvent(this);
        }
        this.bean = bean;
        bean.addPathUpdatedEvent(this);
    }

    /**
     * Creates new form FilePanel
     */
    public FilePanel() {
        logger.trace("FilePanel context inited");
        initComponents();
        logger.trace("FilePanel context up");
        elementsList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        elementsList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                logger.trace("click:" + evt);
                if (evt.getClickCount() == 2) {
                    int index = elementsList.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                        Path selected = elementsList.getModel().getElementAt(index);
                        bean.setPath(selected);
                    }
                }
            }
        });
        logger.trace("FilePanel listeners up");
    }

    private void update() {
        rootComboBox.setModel(new DefaultComboBoxModel<String>(bean.getRoots()));
        DefaultListModel<Path> model = (DefaultListModel<Path>) elementsList.getModel();
        model.clear();
        for (Path i : bean.getElements()) {
            model.addElement(i);
        }
        pathTextField.setText(bean.getPath().getPath());
        boolean ediatable = bean.getRoots().length != 0;
        rootComboBox.setEnabled(ediatable);
        elementsList.setEnabled(ediatable);
        pathTextField.setEnabled(ediatable);
        upButton.setEnabled(ediatable);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        upButton = new javax.swing.JButton();
        rootComboBox = new javax.swing.JComboBox<String>();
        pathTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        elementsList = new javax.swing.JList<Path>();

        setMinimumSize(new java.awt.Dimension(300, 200));

        jToolBar1.setRollover(true);

        upButton.setText("Up");
        upButton.setFocusable(false);
        upButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        upButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        upButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(upButton);

        rootComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] {}));
        jToolBar1.add(rootComboBox);

        pathTextField.setMinimumSize(new java.awt.Dimension(84, 28));
        pathTextField.setPreferredSize(new java.awt.Dimension(84, 28));
        jToolBar1.add(pathTextField);

        elementsList.setModel(new DefaultListModel<Path>());
        jScrollPane1.setViewportView(elementsList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 294,
                                                Short.MAX_VALUE)
                                        .addGroup(
                                                layout.createSequentialGroup().addContainerGap()
                                                        .addComponent(jScrollPane1))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                        .addContainerGap()));
    }// </editor-fold>//GEN-END:initComponents

    private void upButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_upButtonActionPerformed
        bean.setPath(bean.getPath().getParent());
    }// GEN-LAST:event_upButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<Path> elementsList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField pathTextField;
    private javax.swing.JComboBox<String> rootComboBox;
    private javax.swing.JButton upButton;

    // End of variables declaration//GEN-END:variables

    public void update(Direction d) {
        try {
            if (Direction.Local == d) {
                File[] fileRoots = File.listRoots();
                String[] roots = new String[fileRoots.length];
                for (int i = 0; i < roots.length; i++) {
                    roots[i] = fileRoots[i].getPath();
                }
                bean.setRoots(roots);
                bean.setPath(factory.create("file:" + roots[0]));
            } else if (Direction.Remote == d) {
                bean.setRoots(new String[] { "/" });
                bean.setPath(factory.create("yandex:"));
            }
        } catch (PathNotSupportedException e) {
            handler.handle(e);
        }
    }

    protected void calculateElements() {
        bean.setElements(bean.getPath().listDirectoriesAndFiles());
        logger.trace("calculateElements: bean.path=" + bean.getPath());
        logger.trace("calculateElements: bean.elements=" + bean.getElements());
        update();
    }

    public Path getSelected() {
        return elementsList.getSelectedValue();
    }

    @Override
    public void update(Path newPath) {
        calculateElements();

    }
}
