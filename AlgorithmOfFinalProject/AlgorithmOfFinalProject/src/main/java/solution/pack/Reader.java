package solution.pack;

import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.util.ArrayList;  
import java.util.List;  

public class Reader {
    public static List<Object> read(File file) {  
        BufferedReader br = null;  
        List<Object> data = new ArrayList<Object>();  
        try {  
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));  
      //      br.readLine();  
            String inputLine = br.readLine();  
            StringBuilder sb = new StringBuilder("");  
            while(inputLine != null) {  
                sb.append(inputLine + "\n");  
                inputLine = br.readLine();  
            }  
            String[] arr = sb.toString().split("[ \n]");  
            float[] weight = new float[arr.length/2];  
            float[] profit = new float[arr.length/2];  
            for(int i = 0; i < arr.length/2; i++) {  
                weight[i] = Float.parseFloat(arr[2*i]);  
                profit[i] = Float.parseFloat(arr[2*i + 1]);  
            }  
            data.add(weight);  
            data.add(profit);  
            return data;  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                br.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return null;  
    }   
}
 