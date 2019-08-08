package util;

public class Settings {

    public enum Scenes {
        MY_REALTIES("/view/screens/FXMLMyRealties.fxml"),
        SEARCH("/view/screens/FXMLSearch.fxml"),
        REALTY("/view/screens/FXMLRealty.fxml"),
        LOGIN("/view/screens/FXMLLogin.fxml"),
        HOME("/view/screens/FXMLHome.fxml"),
        CONECT_SERVER("/view/screens/FXMLServerConect.fxml"),
        LOADING("/view/screens/FXMLLoading.fxml"),
        NEW_REALTY("/view/screens/FXMLNewRealty.fxml"),
        LOGON("/view/screens/FXMLLogon.fxml");

        private final String value;

        Scenes(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

}
