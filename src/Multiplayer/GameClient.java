package Multiplayer;

import java.io.IOException;
import java.net.*;

import TrashGTAGame.Game;
import TrashGTAGame.Handler;
import TrashGTAGame.ID;
import TrashGTAGame.PlayerMP;

public class GameClient extends Thread {
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Game game;
    private Handler handler;

    public GameClient(Game game, String ipAddress, Handler handler) {
        this.game = game;
        this.handler = handler;
        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
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
                break;
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                handleLogin((Packet00Login) packet, address, port);
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "] " + ((Packet01Disconnect) packet).getUsername() + "Has left the ghetto...");
                break;
            case MOVE:
                packet = new Packet02Move(data);
                handleMove((Packet02Move) packet);
                break;
            case MOVECIV:
                packet = new Packet03MoveCiv(data);
                handleMoveCiv((Packet03MoveCiv) packet);
                break;
        }
    }

    public void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1332);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLogin(Packet00Login packet, InetAddress address, int port) {
        System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername() + "has joined the game...");
        PlayerMP player = new PlayerMP(packet.getX(), packet.getY(), packet.getUsername(), ID.PlayerMP, game, address, port);
        handler.addObject(player);
    }

    private void handleMove(Packet02Move packet) {
        this.handler.movePlayer(packet.getUsername(), packet.getX(), packet.getY());
    }

    private void handleMoveCiv(Packet03MoveCiv packet) {
        this.handler.moveCivilian(packet.getX(), packet.getY(), packet.getIndex(), packet.getCount());
    }


}
