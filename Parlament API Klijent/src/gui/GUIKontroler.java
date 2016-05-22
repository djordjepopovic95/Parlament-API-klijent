package gui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Poslanik;
import util.ParlamentAPIKomunikacija;

public class GUIKontroler {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");;

	public static void popuniTabelu(ParlamentTableModel model){
		try {
			model.ucitajPoslanike(ParlamentAPIKomunikacija.getMembers("data/serviceMembers.json"));
		} catch (ParseException e1) {
			System.out.println(e1.getMessage() + "Greska pri punjenju tabele, tj. citanju iz JSONa.");
			e1.printStackTrace();
		}
	}
	
	public static void sacuvajIzmene(ParlamentTableModel model){
	
		LinkedList<Poslanik> poslanici = model.vratiPoslanike();
		JsonArray poslaniciArray = new JsonArray();

		for (int i = 0; i < poslanici.size(); i++) {
			Poslanik p = poslanici.get(i);

			JsonObject pJson = new JsonObject();
			pJson.addProperty("id", p.getId());
			pJson.addProperty("name", p.getIme());
			pJson.addProperty("lastName", p.getPrezime());
			pJson.addProperty("birthDate", sdf.format(p.getDatumRodjenja()));

			poslaniciArray.add(pJson);
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try {
			PrintWriter out = new PrintWriter(
					new BufferedWriter(new FileWriter("data/updatedMembers.json")));

			String poslaniciString = gson.toJson(poslaniciArray);

			out.println(poslaniciString);
			out.close();
		} catch (Exception e1) {
			System.out.println("Greska: " + e1.getMessage());
		}
		
	}
	
}
