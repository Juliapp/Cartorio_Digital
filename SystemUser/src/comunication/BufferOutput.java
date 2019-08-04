package comunication;

import java.util.Observable;

public class BufferOutput /*extends Observable*/{
    byte[] toOutput;

    public BufferOutput() {
        toOutput = new byte[]{};
    }

    public byte[] getToOutput() {
        return toOutput;
    }

    public void setToOutput(byte[] toOutput) {
        this.toOutput = toOutput;
    }

}
