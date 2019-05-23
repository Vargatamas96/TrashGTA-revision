package Multiplayer;

import TrashGTAGame.Game;
import TrashGTAGame.Handler;
import TrashGTAGame.ID;
import TrashGTAGame.PlayerMP;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread {

    private DatagramSocket socket;
    private Game game;
    public Handler handler;
    private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();

    public GameServer(Game game, Handler handler) {
        this.game = game;
        this.handler = handler;
        try {
            this.socket = new DatagramSocket(1332);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet packet = null;
        switch (type) {
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "] " + ((Packet00Login) packet).getUsername() + "Has connected...");
                PlayerMP player = new PlayerMP(800, 800, ((Packet00Login) packet).getUsername(), ID.PlayerMP, game, address, port);
                this.addConnection(player, (Packet00Login) packet);
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "] " + ((Packet01Disconnect) packet).getUsername() + "Has left...");
                this.removeConnection((Packet01Disconnect) packet);
                break;
            case MOVE:
                packet = new Packet02Move(data);
                System.out.println(((Packet02Move) packet).getUsername() + "has moved to " + ((Packet02Move) packet).getX() + "," + ((Packet02Move) packet).getY());
                this.handleMove(((Packet02Move) packet));
            case MOVECIV:
                packet = new Packet03MoveCiv(data);
                this.handleMoveCiv((Packet03MoveCiv) packet);
        }
    }

    public void addConnection(PlayerMP player, Packet00Login packet) {
        boolean alreadyConnected = false;
        for (PlayerMP p : this.connectedPlayers) {
            if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
                if (p.ipAddress == null) {
                    p.ipAddress = player.ipAddress;
                }
                if (p.port == -1) {
                    p.port = player.port;
                }
                alreadyConnected = true;
            } else {
                //relay to the current connected player, that there is a new player
                sendData(packet.getData(), p.ipAddress, p.port);

                //relay to the new player that the currently connect player exists
                packet = new Packet00Login(p.getUsername(), (int) p.x, (int) p.y);
                sendData(packet.getData(), player.ipAddress, player.port);
            }
        }
        if (!alreadyConnected) {
            this.connectedPlayers.add(player);
        }
    }

    public void removeConnection(Packet01Disconnect packet) {
        this.connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
        packet.writeData(this);
    }

    public PlayerMP getPlayerMP(String username) {
        for (PlayerMP player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public int getPlayerMPIndex(String username) {  //loops through players, if it hits the valid player, it's returning index
        int index = 0;
        for (PlayerMP player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                break;
            }
            index++;
        }
        return index;
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToAllClients(byte[] data) {
        for (PlayerMP p : connectedPlayers) {
            sendData(data, p.ipAddress, p.port);
        }
    }

    private void handleMove(Packet02Move packet) {
        if (getPlayerMP(packet.getUsername()) != null) {
            int index = getPlayerMPIndex(packet.getUsername());
            this.connectedPlayers.get(index).x = packet.getX();
            this.connectedPlayers.get(index).y = packet.getY();
            packet.writeData(this);
        }
    }

    private void handleMoveCiv(Packet03MoveCiv packet) {
        packet.writeData(this);
    }
}
