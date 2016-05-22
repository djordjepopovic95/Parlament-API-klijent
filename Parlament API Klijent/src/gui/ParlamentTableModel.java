package gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import domain.Poslanik;

public class ParlamentTableModel extends AbstractTableModel {

	private final String[] kolone = new String[] { "ID", "Name", "Last name", "Birth date" };
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
	private LinkedList<Poslanik> poslanici;

	public ParlamentTableModel(LinkedList<Poslanik> poslanici) {
		if (poslanici == null) {
			this.poslanici = new LinkedList<>();
		} else {
			this.poslanici = poslanici;
		}
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public int getRowCount() {
		return poslanici.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Poslanik p = poslanici.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return p.getId();
		case 1:
			return p.getIme();
		case 2:
			return p.getPrezime();
		case 3:
			return p.getDatumRodjenja();
		default:
			return "NN";
		}
	}

	@Override
	public String getColumnName(int column) {

		return kolone[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0)
			return false;
		return true;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (isCellEditable(rowIndex, columnIndex)) {
			Poslanik p = poslanici.get(rowIndex);

			switch (columnIndex) {
			case 1:
				if (aValue != null && (String) aValue != "") {
					p.setIme((String) aValue);
				} else
					System.out.println("String ne sme da bude prazan.");
			case 2:
				if (aValue != null && (String) aValue != "") {
					p.setPrezime((String) aValue);
				} else
					System.out.println("String ne sme da bude prazan.");
			case 3:
				try {
					p.setDatumRodjenja(sdf.parse((String) aValue));
				} catch (ParseException e) {
					System.out.println("Datum nije ispravnog formata.");
				}
			}

			poslanici.set(rowIndex, p);
			fireTableDataChanged();
		}
	}

	public void ucitajPoslanike(LinkedList<Poslanik> poslanici) {
		this.poslanici = poslanici;
		fireTableDataChanged();
	}
	
	public LinkedList<Poslanik> vratiPoslanike(){
		return poslanici;
	}

}
