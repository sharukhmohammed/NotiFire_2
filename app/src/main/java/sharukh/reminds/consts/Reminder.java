package sharukh.reminds.consts;

import android.content.Context;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class Reminder
{


    private int ID;
    private String title, text;
    private Date date, dateEnd;
    private boolean isDone, isImportant, isRecurring;

    public Reminder(String title, String text, String timeNatural)
    {
        Parser parser = new Parser();
        List<DateGroup> dateGroup = parser.parse(timeNatural);
        DateGroup parsedResult = dateGroup.get(0);

        this.ID = new Random().nextInt(Integer.SIZE - 1);
        this.title = title;
        this.text = text;
        this.date = parsedResult.getDates().get(0);
        this.dateEnd = parsedResult.getRecursUntil();
        this.isRecurring = parsedResult.isRecurring();
        this.isImportant = false;
        this.isDone = false;
    }

    public static void createReminder(Reminder reminder, Context context)
    {
        //TODO: Create Notification universal logic
    }

    public boolean hasSameId(Object object)
    {
        return object != null && object instanceof Reminder && this.ID == ((Reminder) object).ID;
    }

    @Override
    public boolean equals(Object object)
    {
        return (object != null) && (object instanceof Reminder) && (this.title.equals(((Reminder) object).title));
    }

    @Override
    public String toString()
    {
        return "[ " + String.valueOf(ID) + ". " + title + "\t" + text + "\t" + date.toString() + "]" ;
    }

    @Override
    public int hashCode()
    {
        return 1;
    }

    public static String getTimeAgo(long time)
    {
        final int SECOND_MILLIS = 1000;
        final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        final int DAY_MILLIS = 24 * HOUR_MILLIS;

        if (time < 1000000000000L)
        {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0)
        {
            Date date = new Date(time);
            DateFormat formatter = new SimpleDateFormat("hh:mm aa dd-MMM-yyyy", Locale.getDefault());
            return formatter.format(date);
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS)
        {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS)
        {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS)
        {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS)
        {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS)
        {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS)
        {
            return "yesterday";
        } else
        {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getText()
    {
        return text;
    }
    public void setText(String text)
    {
        this.text = text;
    }
    public Date getDate()
    {
        return date;
    }
    public void setDate(Date date)
    {
        this.date = date;
    }
    public boolean isDone()
    {
        return isDone;
    }
    public void setDone(boolean done)
    {
        isDone = done;
    }
    public boolean isImportant()
    {
        return isImportant;
    }
    public void setImportant(boolean important)
    {
        isImportant = important;
    }

    public int getID()
    {
        return ID;
    }
}
