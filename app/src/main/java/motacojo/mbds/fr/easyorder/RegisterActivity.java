package motacojo.mbds.fr.easyorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import motacojo.mbds.fr.outils.FormValidator;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nom        = (EditText)findViewById(R.id.input_Nom_Register);
    EditText prenom     = (EditText)findViewById(R.id.input_Prenom_Register);
    EditText tel        = (EditText)findViewById(R.id.input_Tel_Register);
    EditText email      = (EditText)findViewById(R.id.input_Email_Register);
    EditText mdp        = (EditText)findViewById(R.id.input_Mdp_Register);
    EditText mdpConfirm = (EditText)findViewById(R.id.input_MdpConfirm_Register);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FormValidator fdNom         = new FormValidator(nom);
        FormValidator fdPrenom      = new FormValidator(prenom);
        FormValidator fdEmail       = new FormValidator(email);
        FormValidator fdTel         = new FormValidator(tel);
        FormValidator fdMdp         = new FormValidator(mdp);
        FormValidator fdMdpConfirm  = new FormValidator(mdpConfirm);
        fdMdpConfirm.identicalTo(mdp);
    }


    @Override
    public void onClick(View v) {
        if (isValide(nom) &&
                isValide(prenom) &&
                isValide(email) &&
                isValide(tel) &&
                isValide(mdp)) {

        }
    }

    public boolean isValide(EditText editText) {
        Editable text = editText.getText();
        int inputType = editText.getInputType();
        Pattern pattern;
        Matcher matcher;
        switch (inputType) {
            case InputType.TYPE_CLASS_PHONE :
                pattern = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");
                matcher = pattern.matcher(text.toString());
                if (!matcher.matches()) {
                    editText.setError("Le numéro de téléphone n'est pas valide.");
                    return false;
                } else {
                    editText.setError(null);
                    return true;
                }
            case 33:
                pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
                matcher = pattern.matcher(text.toString());
                if (!matcher.matches()) {
                    editText.setError("L'adresse e-mail doit être de la forme exemple@exemple.com.");
                    return false;
                } else {
                    editText.setError(null);
                    return true;
                }
            case InputType.TYPE_TEXT_VARIATION_PASSWORD :
                pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
                matcher = pattern.matcher(text.toString());
                if (!matcher.matches()) {
                    editText.setError("Le mot de passe doit avoir au moins 8 caractères, dont au moins un minuscul, un majuscul et un chiffre.");
                    return false;
                } else {
                    editText.setError(null);
                    return true;
                }
            default:
                if (TextUtils.isEmpty(text)) {
                    editText.setError("Ce champ est obligatoire!");
                    return false;
                } else {
                    editText.setError(null);
                    return true;
                }
        }
    }
}
