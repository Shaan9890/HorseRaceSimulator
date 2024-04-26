import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.text.ParseException;

/* Used in implementation of editing the lane for a horse.
** Allows the user to enter an empty string in the text field for a lane.
 */
public class LaneFormatter extends NumberFormatter {
    @Override
    public Object stringToValue(String text) throws ParseException {
        if (text.isEmpty()) {
            return null;
        }
        return super.stringToValue(text);
    }

    LaneFormatter (NumberFormat format){
        super(format);
    }
}
