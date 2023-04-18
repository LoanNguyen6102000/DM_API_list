package excel.json;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import static excel.json.MainClass.LINK_NAME;

public class JsonToExcel {

    public static void exportSwaggerUrlToExcel(String swaggerUrl) throws Exception{
        // TODO Auto-generated method stub
        System.out.println(LINK_NAME);
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("API Details");

        String jsonString = readUrl(swaggerUrl);
        JSONParser parser = new JSONParser();
        
        JSONObject json = (JSONObject) parser.parse(jsonString);
        JSONObject paths = (JSONObject) json.get("paths");
        
        HashMap<String, JSONObject> apiSet = (HashMap<String, JSONObject>) paths;
        HashMap<String, JSONObject> API_SET = new HashMap<>();

        int count = 0;
        int rownum = 0;

        Row row = sheet.createRow(rownum++);
        String [] objArr = new String[] {
                "S.No.",
                "DESCRIPTION",
                "CONTROLLER",
                "METHOD",
                "OPERATION_ID",
                "API_URL",
                "RESPONSE_TYPE"
        };
        int cellnum = 0;

        if (apiSet != null) {
            for (String key : apiSet.keySet()) {
                Set methods = apiSet.get(key).keySet();
                for (Object method : methods) {
                    API_SET.put(key + "_" + method.toString(), apiSet.get(key));
                }
            }

            for (String obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                cell.setCellValue(obj);
            }

            rownum++;

            for (Entry<String, JSONObject> e : API_SET.entrySet()) {
                String url = e.getKey().split("_")[0];
                String METHOD = e.getKey().split("_")[1];
                JSONObject api = e.getValue();

                JSONObject method = (JSONObject) api.get(METHOD);

                String controller = method.get("tags").toString();
                String description = (String) method.get("summary");
                String operationId = (String) method.get("operationId");

                JSONObject response = ((JSONObject)((JSONObject)method.get("responses")).get("200"));

                int statusEnding = 1;
                while (response == null) {
                    String responseStatus = "20" + statusEnding;
                    response = ((JSONObject)((JSONObject)method.get("responses")).get(responseStatus));
                    statusEnding++;
                }

                JSONObject schema = (JSONObject) response.get("schema");
                String responses = null;

                if(schema != null){
                    responses = (String)schema.get("$ref");

                    if(responses == null) {

                        if(schema.get("type").equals("array"))
                            responses = (String)((JSONObject)schema.get("items")).get("$ref");
                        else if((schema.get("type")).equals("object") && schema.keySet().toString().contains("additionalProperties"))
                            responses = (String)((JSONObject)schema.get("additionalProperties")).get("type");
                    }

                    if(responses != null){
                        String[] res = responses.split("/");
                        responses = res[res.length - 1];
                    }
                }
                System.out.println("api:" + url +
                        "\tmethod:" + METHOD.toUpperCase() +
                        "\tcontroller:" + controller +
                        "\toperationId:" + operationId +
                        "\tdescription:" + description +
                        "\tresponses:" + responses
                );

                row = sheet.createRow(rownum++);
                objArr = new String[] {
                        description,
                        controller,
                        METHOD.toUpperCase(),
                        operationId,
                        url,
                        responses
                };
                cellnum = 0;

                Cell cell = row.createCell(cellnum++);
                cell.setCellValue(++count);

                for (String obj : objArr)
                {
                    cell = row.createCell(cellnum++);
                    cell.setCellValue(obj);

                }
            }
        }
        System.out.println(count);

        try
        {
            //Write the workbook in file system
            String fileName = LINK_NAME.split("/")[2] + ".xlsx";
            FileOutputStream out = new FileOutputStream(fileName);
            workbook.write(out);
            out.close();
            System.out.println(fileName + " written successfully on disk.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
