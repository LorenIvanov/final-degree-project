package speechy;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import marytts.TextToSpeech;
import marytts.signalproc.effects.JetPilotEffect;
import marytts.signalproc.effects.RobotiserEffect;
import net.sourceforge.javaflacencoder.FLACFileWriter;

import javax.sound.sampled.LineUnavailableException;
import java.util.Date;

public class SpeechyBot {
    private final TextToSpeech tts = new TextToSpeech();
    private final Microphone mic = new Microphone(FLACFileWriter.FLAC);
    private final GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
    private String oldText = "";
    private boolean recognizing = true;

    public boolean start() {
        duplex.setLanguage("en");

        duplex.addResponseListener(googleResponse -> {
            String output;

            //Get the response from Google Cloud
            output = googleResponse.getResponse();
            System.out.println(output);
            if (output != null) {
                AI(output);
            } else
                System.out.println("Output was null");
        });

        // Setting the Current Voice
        tts.setVoice("cmu-slt-hsmm");

        //JetPilotEffect
//        JetPilotEffect jetPilotEffect = new JetPilotEffect();
//        jetPilotEffect.setParams("amount:100");

        RobotiserEffect robotiserEffect = new RobotiserEffect();
        robotiserEffect.setParams("amount:50");

        //Apply the effects
        tts.getMarytts().setAudioEffects(robotiserEffect.getFullEffectAsString());

        //Start the Speech Recognition
        startSpeechy();
        while (recognizing) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * This method makes a decision based on the given text of the Speech Recognition
     *
     * @param output
     */
    public void AI(String output) {
        output = output.trim();
        if (!oldText.equals(output))
            oldText = output;
        else
            return;

        if (output.contains("hello")) {
            speak("Who is there");

        } else if (output.contains("loren")) {
            speak("hello loren.");

        } else if (output.contains("introduce yourself")) {
            speak("My name is Speechy, I am robot.");

        } else if (output.contains("who are you")) {
            speak("My name is Speechy, I am robot.");

        } else if (output.contains("what is your profession")) {
            speak("I am a Robot.");

        } else if (output.contains("where do you live")) {
            speak("Inside the computer.");

        } else if (output.contains("what's the time") || output.contains("what is the time")) {
            speak(new Date().toString());

        } else if (output.contains("what's the date") || output.contains("what is the date")) {
            speak("twenty seventh of september two thousand and nineteen.");

        } else if (output.equals("stop")) {
            mic.close();
            recognizing = false;

        } else {
            System.out.println("Speechy can't recognize voice command. Try again.");
        }

    }

    /**
     * Starts the Speech Recognition
     */
    public void startSpeechy() {
        //Start a new Thread so our application don't lags
        new Thread(() -> {
            try {
                duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
            } catch (LineUnavailableException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Calls the MaryTTS to say the given text
     *
     * @param text
     */
    public void speak(String text) {
        if (!tts.isSpeaking())
            new Thread(() -> tts.speak(text, 2.0f, true, false)).start();

    }
}
