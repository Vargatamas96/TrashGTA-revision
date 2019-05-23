package Multiplayer;

public class Packet03MoveCiv extends Packet {

    private int x,y,index,count;

    public Packet03MoveCiv(byte[] data) {
        super(03);
        String[] dataArray = readData(data).split(",");
        this.x = Integer.parseInt(dataArray[0]);
        this.y = Integer.parseInt(dataArray[1]);
        this.index = Integer.parseInt(dataArray[2]);
        this.count = Integer.parseInt(dataArray[3]);
    }

    public Packet03MoveCiv(int x, int y, int index, int count) {
        super(03);
        this.x = x;
        this.y = y;
        this.index = index;
        this.count = count;
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("03" + this.x + "," + this.y + "," + this.index + "," + this.count).getBytes();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getIndex() {
        return index;
    }

    public int getCount() {
        return count;
    }
}
