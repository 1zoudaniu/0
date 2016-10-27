package safebox.yiye.com.safebox.BroadcastReceiver;

public  class BroadCastActivity_SMS
//        extends BroadcastReceiver
{

//    public static SmsMessage[] getMessageFromIntent(Intent intent) {
//        SmsMessage retmeMessage[] = null;
//        Bundle bundle = intent.getExtras();
//        Object pdus[] = (Object[]) bundle.get("pdus");
//        retmeMessage = new SmsMessage[pdus.length];
//        for (int i = 0; i < pdus.length; i++) {
//            byte[] bytedata = (byte[]) pdus[i];
//            retmeMessage[i] = SmsMessage.createFromPdu(bytedata);
//        }
//        return retmeMessage;
//    }
//
//    public static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//// TODO Auto-generated method stub
//        if (ACTION.equals(intent.getAction())) {
//            Intent i = new Intent(context, BroadCastActivity_SMS.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            SmsMessage[] msgs = getMessageFromIntent(intent);
//
//            StringBuilder sBuilder = new StringBuilder();
//            if (msgs != null && msgs.length > 0) {
//                for (SmsMessage msg : msgs) {
//                    if (msg.getDisplayOriginatingAddress().startsWith("106571207117008")) {
//                        String displayMessageBody = msg.getDisplayMessageBody();
//                        String substring = displayMessageBody.substring(displayMessageBody.length() - 4);
//                        i.putExtra("sms_body", substring);
//                    }
//                }
//            }
//            Toast.makeText(context, sBuilder.toString(), Toast.LENGTH_LONG).show();
//
//        }
//    }
}