package persistence;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.*;
import service.RapportQuotidienVol;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class JsonDataManager {

    private static final String DATA_FOLDER = "DATA";


    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .registerTypeAdapter(Utilisateur.class, new UtilisateurAdapter())
            .registerTypeAdapter(ProprieteVolee.class, new ProprieteVoleeAdapter())
            .create();

    static {
        Path dataPath = Paths.get(DATA_FOLDER);
        if (!Files.exists(dataPath)) {
            try {
                Files.createDirectories(dataPath);
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du dossier DATA: " + e.getMessage());
            }
        }
    }

    public static <T> void sauvegarder(String nomFichier, List<T> donnees) {
        String cheminFichier = DATA_FOLDER + File.separator + nomFichier + ".json";
        try (Writer writer = new FileWriter(cheminFichier)) {
            gson.toJson(donnees, writer);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde de " + nomFichier + ": " + e.getMessage());
        }
    }

    public static <T> List<T> charger(String nomFichier, Type type) {
        String cheminFichier = DATA_FOLDER + File.separator + nomFichier + ".json";
        File fichier = new File(cheminFichier);
        
        if (!fichier.exists()) {
            return new ArrayList<>();
        }
        
        try (Reader reader = new FileReader(cheminFichier)) {
            List<T> donnees = gson.fromJson(reader, type);
            return donnees != null ? donnees : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de " + nomFichier + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Victime> chargerVictimes() {
        Type type = new TypeToken<List<Victime>>(){}.getType();
        return charger("victimes", type);
    }

    public static void sauvegarderVictimes(List<Victime> victimes) {
        sauvegarder("victimes", victimes);
    }

    public static List<Temoin> chargerTemoins() {
        Type type = new TypeToken<List<Temoin>>(){}.getType();
        return charger("temoins", type);
    }

    public static void sauvegarderTemoins(List<Temoin> temoins) {
        sauvegarder("temoins", temoins);
    }

    public static List<Declaration> chargerDeclarations() {
        Type type = new TypeToken<List<Declaration>>(){}.getType();
        return charger("declarations", type);
    }

    public static void sauvegarderDeclarations(List<Declaration> declarations) {
        sauvegarder("declarations", declarations);
    }

    public static List<RapportQuotidienVol> chargerRapports() {
        Type type = new TypeToken<List<RapportQuotidienVol>>(){}.getType();
        return charger("rapports", type);
    }

    public static void sauvegarderRapports(List<RapportQuotidienVol> rapports) {
        sauvegarder("rapports", rapports);
    }

    private static class UtilisateurAdapter implements JsonSerializer<Utilisateur>, JsonDeserializer<Utilisateur> {
        private static final String TYPE_FIELD = "type";
        
        @Override
        public JsonElement serialize(Utilisateur src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(TYPE_FIELD, src.getClass().getSimpleName());
            jsonObject.addProperty("numeroCNI", src.getNumeroCNI());
            jsonObject.addProperty("nom", src.getNom());
            jsonObject.addProperty("prenom", src.getPrenom());
            jsonObject.addProperty("adresse", src.getAdresse());
            jsonObject.addProperty("telephone", src.getTelephone());
            return jsonObject;
        }

        @Override
        public Utilisateur deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get(TYPE_FIELD).getAsString();
            String numeroCNI = jsonObject.get("numeroCNI").getAsString();
            String nom = jsonObject.get("nom").getAsString();
            String prenom = jsonObject.get("prenom").getAsString();
            String adresse = jsonObject.get("adresse").getAsString();
            String telephone = jsonObject.get("telephone").getAsString();
            
            if ("Victime".equals(type)) {
                return new Victime(numeroCNI, nom, prenom, adresse, telephone);
            } else if ("Temoin".equals(type)) {
                return new Temoin(numeroCNI, nom, prenom, adresse, telephone);
            }
            throw new JsonParseException("Type d'utilisateur inconnu: " + type);
        }
    }

    private static class ProprieteVoleeAdapter implements JsonSerializer<ProprieteVolee>, JsonDeserializer<ProprieteVolee> {
        private static final String TYPE_FIELD = "type";
        
        @Override
        public JsonElement serialize(ProprieteVolee src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(TYPE_FIELD, src.getClass().getSimpleName());
            jsonObject.addProperty("couleur", src.getCouleur());
            jsonObject.addProperty("marque", src.getMarque());
            
            if (src instanceof Vehicule) {
                jsonObject.addProperty("matricule", ((Vehicule) src).getMatricule());
            } else if (src instanceof Velo) {
                jsonObject.addProperty("numeroSerie", ((Velo) src).getNumeroSerie());
            }
            return jsonObject;
        }

        @Override
        public ProprieteVolee deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get(TYPE_FIELD).getAsString();
            String couleur = jsonObject.get("couleur").getAsString();
            String marque = jsonObject.get("marque").getAsString();
            
            if ("Vehicule".equals(type)) {
                String matricule = jsonObject.get("matricule").getAsString();
                return new Vehicule(couleur, marque, matricule);
            } else if ("Velo".equals(type)) {
                String numeroSerie = jsonObject.get("numeroSerie").getAsString();
                return new Velo(couleur, marque, numeroSerie);
            }
            throw new JsonParseException("Type de propriété volée inconnu: " + type);
        }
    }
}
