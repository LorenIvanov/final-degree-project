import marytts.signalproc.effects.*;

import java.util.Scanner;

public class SpeechyTextToSpeech {
    {
        //VocalTractLinearScalerEffect
        vocalTractLSE = new VocalTractLinearScalerEffect(); //russian drunk effect
        vocalTractLSE.setParams("amount:70");

        //JetPilotEffect
        jetPilotEffect = new JetPilotEffect(); //epic fun!!!
        jetPilotEffect.setParams("amount:100");

        //RobotiserEffect
        robotiserEffect = new RobotiserEffect();
        robotiserEffect.setParams("amount:50");

        //StadiumEffect
        stadiumEffect = new StadiumEffect();
        stadiumEffect.setParams("amount:150");

        //LpcWhisperiserEffect
        lpcWhisperiserEffect = new LpcWhisperiserEffect(); //creepy
        lpcWhisperiserEffect.setParams("amount:70");

        //VolumeEffect
        volumeEffect = new VolumeEffect(); //be careful with this i almost got heart attack
        volumeEffect.setParams("amount:10");
    }

    private static VocalTractLinearScalerEffect vocalTractLSE;
    private static JetPilotEffect jetPilotEffect;
    private static RobotiserEffect robotiserEffect;
    private static StadiumEffect stadiumEffect;
    private static LpcWhisperiserEffect lpcWhisperiserEffect;
    private static VolumeEffect volumeEffect;

    public boolean start() {
        boolean exit;
        Scanner sc = new Scanner(System.in);
        TextToSpeech tts = new TextToSpeech();
        tts.getMarytts().setAudioEffects(jetPilotEffect.getFullEffectAsString());
        while (true) {
            System.out.println("Enter text:");
            String text = sc.next();
            tts.speak(text, 2.0f, false, true);

            System.out.println("Do you want to enter another text? (y/n)");
            String userChoice = "";
            do {
                userChoice = sc.next();
            } while (!(userChoice.equals("y") || userChoice.equals("n")));

            if (userChoice.equals("n")) {

                System.out.println("Do you want to go back to the main menu? (y/n)");
                String userChoiceMainMenu = "";
                do {
                    userChoiceMainMenu = sc.next();
                } while (!(userChoiceMainMenu.equals("y") || userChoiceMainMenu.equals("n")));
                exit = userChoiceMainMenu.equals("n");
                break;
            }
        }
        return exit;
    }
}
