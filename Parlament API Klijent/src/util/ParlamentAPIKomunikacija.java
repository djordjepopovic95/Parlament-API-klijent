package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domain.Poslanik;

public class ParlamentAPIKomunikacija {

	private static final String membersURL = "http://147.91.128.71:9090/parlament/api/members";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");

	private static String sendGet(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		boolean endReading = false;
		String response = "";

		while (!endReading) {
			String s = in.readLine();

			if (s != null) {
				response += s;
			} else {
				endReading = true;
			}
		}
		in.close();

		return response.toString();
	}

	public static LinkedList<Poslanik> vratiPoslanike(String putanja) throws ParseException {
		try {
			FileReader reader = new FileReader(putanja);

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			JsonArray poslaniciJson = gson.fromJson(reader, JsonArray.class);

			LinkedList<Poslanik> poslanici = new LinkedList<Poslanik>();

			for (int i = 0; i < poslaniciJson.size(); i++) {
				JsonObject poslanikJson = (JsonObject) poslaniciJson.get(i);

				Poslanik p = new Poslanik();
				p.setId(poslanikJson.get("id").getAsInt());
				p.setIme(poslanikJson.get("name").getAsString());
				p.setPrezime(poslanikJson.get("lastName").getAsString());
				if (poslanikJson.get("birthDate") != null)
					p.setDatumRodjenja(sdf.parse(poslanikJson.get("birthDate").getAsString()));

				poslanici.add(p);
			}

			return poslanici;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new LinkedList<Poslanik>();
	}

	public static void sacuvajJSON(String putanja) {
		String result;
		try {
			result = sendGet(membersURL);

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(putanja)));

			out.println(result);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
