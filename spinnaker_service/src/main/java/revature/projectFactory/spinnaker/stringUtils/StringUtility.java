package revature.projectFactory.spinnaker.stringUtils;

import java.util.List;

public class StringUtility {
    public static String asCommaSeperatedString(List<String> list){
        StringBuilder result = new StringBuilder();
            for(int i=0; i<list.size(); i++){
                result.append(list.get(i));
                if(i!=list.size()-1){
                    result.append(",");
                }
            }
        return result.toString();
    }
}
