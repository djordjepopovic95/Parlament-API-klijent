package gui;

import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Poslanik;
import util.ParlamentAPIKomunikacija;

public class GUIKontroler {

	private static ParlamentGUI glavniProzor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					glavniProzor = new ParlamentGUI();
					glavniProzor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");;

	public static void popuniTabelu(ParlamentTableModel model) {
		try {
			model.ucitajPoslanike(ParlamentAPIKomunikacija.vratiPoslanike("data/serviceMembers.json"));
		} catch (ParseException e1) {
			JOptionPane.showMessageDialog(glavniProzor, "Greska pri punjenju tabele, tj. citanju iz JSONa.", "Greska!",
					JOptionPane.ERROR_MESSAGE);

			e1.printStackTrace();
		}
	}

	public static void sacuvajIzmene(ParlamentTableModel model) {

		LinkedList<Poslanik> poslanici = model.vratiPoslanike();
		JsonArray poslaniciArray = new JsonArray();

		for (int i = 0; i < poslanici.size(); i++) {
			Poslanik p = poslanici.get(i);

			JsonObject pJson = new JsonObject();
			pJson.addProperty("id", p.getId());
			pJson.addProperty("name", p.getIme());
			pJson.addProperty("lastName", p.getPrezime());
			if (p.getDatumRodjenja() != null) {
				pJson.addProperty("birthDate", sdf.format(p.getDatumRodjenja()));
			}

			poslaniciArray.add(pJson);
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data/updatedMembers.json")));

			String poslaniciString = gson.toJson(poslaniciArray);

			out.println(poslaniciString);
			out.close();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor, "Greska pri cuvanju izmena.", "Greska!",
					JOptionPane.ERROR_MESSAGE);

		}

	}

}
