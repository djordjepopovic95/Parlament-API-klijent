package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Poslanik;
import util.ParlamentAPIKomunikacija;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

public class ParlamentGUI extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPaneSouth;
	private JTextArea textAreaSouth;
	private JPanel panelEast;
	private JButton btnGetMembers;
	private JButton btnFillTable;
	private JButton btnUpdateMembers;
	private JScrollPane scrollPaneCenter;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public ParlamentGUI() {
		setMinimumSize(new Dimension(600, 400));
		setSize(new Dimension(1500, 1000));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getScrollPaneSouth(), BorderLayout.SOUTH);
		contentPane.add(getPanelEast(), BorderLayout.EAST);
		contentPane.add(getScrollPaneCenter(), BorderLayout.CENTER);
	}

	private JScrollPane getScrollPaneSouth() {
		if (scrollPaneSouth == null) {
			scrollPaneSouth = new JScrollPane();
			scrollPaneSouth.setViewportBorder(
					new TitledBorder(null, "STATUS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			scrollPaneSouth.setPreferredSize(new Dimension(2, 70));
			scrollPaneSouth.setViewportView(getTextAreaSouth());
		}
		return scrollPaneSouth;
	}

	private JTextArea getTextAreaSouth() {
		if (textAreaSouth == null) {
			textAreaSouth = new JTextArea();
		}
		return textAreaSouth;
	}

	private JPanel getPanelEast() {
		if (panelEast == null) {
			panelEast = new JPanel();
			GridBagLayout gbl_panelEast = new GridBagLayout();
			gbl_panelEast.columnWidths = new int[] { 97, 0 };
			gbl_panelEast.rowHeights = new int[] { 23, 0, 0, 0 };
			gbl_panelEast.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
			gbl_panelEast.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
			panelEast.setLayout(gbl_panelEast);
			GridBagConstraints gbc_btnGetMembers = new GridBagConstraints();
			gbc_btnGetMembers.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnGetMembers.insets = new Insets(0, 0, 5, 0);
			gbc_btnGetMembers.anchor = GridBagConstraints.NORTH;
			gbc_btnGetMembers.gridx = 0;
			gbc_btnGetMembers.gridy = 0;
			panelEast.add(getBtnGetMembers(), gbc_btnGetMembers);
			GridBagConstraints gbc_btnFillTable = new GridBagConstraints();
			gbc_btnFillTable.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnFillTable.insets = new Insets(0, 0, 5, 0);
			gbc_btnFillTable.gridx = 0;
			gbc_btnFillTable.gridy = 1;
			panelEast.add(getBtnFillTable(), gbc_btnFillTable);
			GridBagConstraints gbc_btnUpdateMembers = new GridBagConstraints();
			gbc_btnUpdateMembers.gridx = 0;
			gbc_btnUpdateMembers.gridy = 2;
			panelEast.add(getBtnUpdateMembers(), gbc_btnUpdateMembers);
		}
		return panelEast;
	}

	private JButton getBtnGetMembers() {
		if (btnGetMembers == null) {
			btnGetMembers = new JButton("GET Members");
			btnGetMembers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ParlamentAPIKomunikacija.sacuvajJSON("data/serviceMembers.json");
					textAreaSouth.setText(textAreaSouth.getText() + "Poslanici preuzeti sa servisa." + "\n");
				}
			});
		}
		return btnGetMembers;
	}

	private JButton getBtnFillTable() {
		if (btnFillTable == null) {
			btnFillTable = new JButton("Fill table");
			btnFillTable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					GUIKontroler.popuniTabelu((ParlamentTableModel) table.getModel());
					textAreaSouth.setText(textAreaSouth.getText() + "Tabela popunjena preuzetim podacima." + "\n");

				}
			});
		}
		return btnFillTable;
	}

	private JButton getBtnUpdateMembers() {
		if (btnUpdateMembers == null) {
			btnUpdateMembers = new JButton("Update members");
			btnUpdateMembers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GUIKontroler.sacuvajIzmene((ParlamentTableModel) table.getModel());
					textAreaSouth.setText(textAreaSouth.getText() + "Izmene su sacuvane." + "\n");
				}
			});
		}
		return btnUpdateMembers;
	}

	private JScrollPane getScrollPaneCenter() {
		if (scrollPaneCenter == null) {
			scrollPaneCenter = new JScrollPane();
			scrollPaneCenter.setViewportView(getTable());
		}
		return scrollPaneCenter;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			ParlamentTableModel model = new ParlamentTableModel(null);
			table.setModel(model);
		}
		return table;
	}

}
