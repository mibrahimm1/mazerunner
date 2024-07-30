package com.ibrahim.engine;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MapConverter {
    public static void csvToTmx(String csvFile, String tmxFile, int tileWidth, int tileHeight, int mapWidth, int mapHeight) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        // Create map element
        Element mapElem = document.createElement("map");
        mapElem.setAttribute("version", "1.0");
        mapElem.setAttribute("tiledversion", "1.2.4");
        mapElem.setAttribute("orientation", "orthogonal");
        mapElem.setAttribute("width", Integer.toString(mapWidth));
        mapElem.setAttribute("height", Integer.toString(mapHeight));
        mapElem.setAttribute("tilewidth", Integer.toString(tileWidth));
        mapElem.setAttribute("tileheight", Integer.toString(tileHeight));
        document.appendChild(mapElem);

        // Create tileset element
        Element tilesetElem = document.createElement("tileset");
        tilesetElem.setAttribute("firstgid", "1");
        tilesetElem.setAttribute("name", "Tileset");
        tilesetElem.setAttribute("tilewidth", Integer.toString(tileWidth));
        tilesetElem.setAttribute("tileheight", Integer.toString(tileHeight));
        tilesetElem.setAttribute("columns", "0");
        tilesetElem.setAttribute("source", "tileset.tsx");
        mapElem.appendChild(tilesetElem);

        // Create layer element
        Element layerElem = document.createElement("layer");
        layerElem.setAttribute("name", "Tile Layer 1");
        layerElem.setAttribute("width", Integer.toString(mapWidth));
        layerElem.setAttribute("height", Integer.toString(mapHeight));
        mapElem.appendChild(layerElem);

        // Create data element
        Element dataElem = document.createElement("data");
        dataElem.setAttribute("encoding", "csv");
        layerElem.appendChild(dataElem);

        // Read CSV and add to data element
        StringBuilder csvContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                csvContent.append(line).append("\n");
            }
        }

        dataElem.setTextContent(csvContent.toString().trim());

        // Write XML to file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new FileWriter(tmxFile));

        transformer.transform(domSource, streamResult);
    }

    // Convert text map file to CSV
    public static void convertTextToCSV(String inputFile, String outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(MapConverter.class.getResourceAsStream(inputFile)));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Ensure that the line is trimmed and split by spaces
                String[] tiles = line.trim().split("\\s+");
                // Join the tiles with commas
                String csvLine = String.join(",", tiles);
                writer.println(csvLine);
            }
        }
    }

    // Convert CSV file to text map
    public static void convertCSVToText(String inputFile, String outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Ensure that the line is trimmed and split by commas
                String[] tiles = line.trim().split(",");
                // Join the tiles with spaces
                String textLine = String.join(" ", tiles);
                writer.println(textLine);
            }
        }
    }

    // Method to read the grid from a text file
    private int[][] readGridFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int width = -1;
            int height = 0;
            StringBuilder content = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
                if (width == -1) {
                    width = line.split(",").length;
                }
                height++;
            }

            int[][] map = new int[height][width];
            String[] rows = content.toString().split("\n");

            for (int y = 0; y < height; y++) {
                String[] values = rows[y].split(",");
                for (int x = 0; x < width; x++) {
                    map[y][x] = Integer.parseInt(values[x].trim());
                }
            }

            return map;
        }
    }

    public void convertToTmx(int[][] map, int width, int height, String outputPath) {
        try {
            // Create a new XML document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Root element
            Element mapElement = doc.createElement("map");
            mapElement.setAttribute("version", "1.0");
            mapElement.setAttribute("tiledversion", "1.9.2"); // Change if needed
            mapElement.setAttribute("orientation", "orthogonal");
            mapElement.setAttribute("width", Integer.toString(width));
            mapElement.setAttribute("height", Integer.toString(height));
            mapElement.setAttribute("tilewidth", "32"); // Example tile width
            mapElement.setAttribute("tileheight", "32"); // Example tile height
            doc.appendChild(mapElement);

            // Tile Layer
            Element layerElement = doc.createElement("layer");
            layerElement.setAttribute("name", "Layer1");
            layerElement.setAttribute("width", Integer.toString(width));
            layerElement.setAttribute("height", Integer.toString(height));
            mapElement.appendChild(layerElement);

            // Data Element
            Element dataElement = doc.createElement("data");
            dataElement.setAttribute("encoding", "csv");
            layerElement.appendChild(dataElement);

            // Fill data element with map data
            StringBuilder csvData = new StringBuilder();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    csvData.append(map[y][x]).append(",");
                }
                csvData.setLength(csvData.length() - 1); // Remove last comma
                csvData.append("\n");
            }
            dataElement.appendChild(doc.createTextNode(csvData.toString()));

            // Write the content into the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outputPath));
            transformer.transform(source, result);

            System.out.println("TMX file created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String inputTextFile = "/maps/world02.txt";
        String outputCSVFile = "world02.csv";
        String inputCSVFile = "Worldv4.csv";
        String outputTextFile = "worldv4.txt";
        String outputTMXFile = "world02.tmx";

        try {
            // Convert text to CSV
            convertTextToCSV(inputTextFile, outputCSVFile);
            System.out.println("Text to CSV conversion completed.");

            // Read the grid from the CSV file
            MapConverter converter = new MapConverter();
            int[][] map = converter.readGridFromFile(outputCSVFile);

            // Convert grid to TMX
            converter.convertToTmx(map, 50, 50, outputTMXFile);
            System.out.println("CSV to TMX conversion completed.");

            // Convert CSV back to text
            convertCSVToText(inputCSVFile, outputTextFile);
            System.out.println("CSV to text conversion completed.");

        } catch (IOException e) {
            System.err.println("Error during file conversion: " + e.getMessage());
        }
    }


    }

