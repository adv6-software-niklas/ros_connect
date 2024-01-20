package com.ros.connect.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.*;
import com.ros.connect.DTO.ProcessingDTO;
import com.ros.connect.config.rabbitQueue.RosConfigQueue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.stereotype.Service;

import java.util.regex.*;

@Service
public class Connection {
    private final RosConfigQueue props;
    private final testsend _sender;
    private final ObjectMapper mapper;

    public Connection(RosConfigQueue config, testsend sender, ObjectMapper mapper) {
        props = config;
        _sender = sender;
        this.mapper = mapper;
    };

    public boolean Connect() {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(props.username(), props.host(), props.port());
            session.setPassword(props.password());

            // Set up authentication (password or key-based)

            // Avoid strict host key checking
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            // Setup for running the 'ifconfig' command
            String command = "source /opt/ros/iron/setup.bash && ros2 topic echo /chatter";
            try {
                ChannelExec channel = (ChannelExec) session.openChannel("exec");
                channel.setCommand(command);

                // Get the input stream from the channel
                try (InputStream in = channel.getInputStream()) {
                    // Execute the command
                    channel.connect();

                    int connectionTimeout = 5000;

                    // Read the output of the command
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    long startTime = System.currentTimeMillis();

                    String line;
                    Matcher matcher;
                    Pattern regex = Pattern.compile("'([^']*)'");
                    while ((line = reader.readLine()) != null) {
                        matcher = regex.matcher(line);
                        if(matcher.find()) {
                            System.out.println(matcher.group());
                            _sender.sendConfigRequest(matcher.group());
                        }
                        // System.out.println(line);
                        // System.out.println(line.split("\\s").length);
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - startTime >= connectionTimeout) {
                            channel.disconnect();
                            break;
                        }
                    }
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                // Disconnect the session
                if (session != null && session.isConnected()) {
                    session.disconnect();
                    System.out.println("We reached the end of the line of the connection.");
                }
            }
        } catch (JSchException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean Connect(String username, String password, int port, String host) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, host, port);
            session.setPassword(password);

            // Set up authentication (password or key-based)

            // Avoid strict host key checking
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            // Setup for running the 'ifconfig' command
            String command = "source /opt/ros/iron/setup.bash && ros2 topic echo /chatter";
            try {
                ChannelExec channel = (ChannelExec) session.openChannel("exec");
                channel.setCommand(command);

                // Get the input stream from the channel
                try (InputStream in = channel.getInputStream()) {
                    // Execute the command
                    channel.connect();

                    int connectionTimeout = 5000;

                    // Read the output of the command
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    long startTime = System.currentTimeMillis();

                    String line;
                    Matcher matcher;
                    Pattern regex = Pattern.compile("'([^']*)'");
                    while ((line = reader.readLine()) != null) {
                        matcher = regex.matcher(line);
                        if(matcher.find()) {
                            System.out.println(matcher.group());
                            _sender.sendProcessingMessage(matcher.group());
                        }
                        // System.out.println(line);
                        // System.out.println(line.split("\\s").length);
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - startTime >= connectionTimeout) {
                            channel.disconnect();
                            break;
                        }
                    }
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                // Disconnect the session
                if (session != null && session.isConnected()) {
                    session.disconnect();
                    System.out.println("We reached the end of the line of the connection.");
                }
            }
        } catch (JSchException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean Connect(String username, String password, int port, String host, UUID id) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, host, port);
            session.setPassword(password);

            // Set up authentication (password or key-based)

            // Avoid strict host key checking
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            // Setup for running the 'ifconfig' command
            String command = "source /opt/ros/iron/setup.bash && ros2 topic echo /chatter";
            try {
                ChannelExec channel = (ChannelExec) session.openChannel("exec");
                channel.setCommand(command);

                // Get the input stream from the channel
                try (InputStream in = channel.getInputStream()) {
                    // Execute the command
                    channel.connect();

                    int connectionTimeout = 5000;

                    // Read the output of the command
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    long startTime = System.currentTimeMillis();

                    String line;
                    Matcher matcher;
                    Pattern regex = Pattern.compile("'([^']*)'");
                    while ((line = reader.readLine()) != null) {
                        matcher = regex.matcher(line);
                        if(matcher.find()) {
                            System.out.println(matcher.group(1));
                            ProcessingDTO dto = new ProcessingDTO(id, matcher.group(1));
                            String json = mapper.writeValueAsString(dto);
                            _sender.sendProcessingMessage(json);
                        }
                        // System.out.println(line);
                        // System.out.println(line.split("\\s").length);
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - startTime >= connectionTimeout) {
                            channel.disconnect();
                            break;
                        }
                    }
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                // Disconnect the session
                if (session != null && session.isConnected()) {
                    session.disconnect();
                    System.out.println("We reached the end of the line of the connection.");
                }
            }
        } catch (JSchException e) {
            e.printStackTrace();
            return false;
        }
    }

    public CompletableFuture<AtomicBoolean> connectAsync() {
        AtomicBoolean didIFailOrDidIsucceedDotCom = new AtomicBoolean(false);
        return CompletableFuture.supplyAsync(() -> {
            try {
                JSch jsch = new JSch();
                Session session = jsch.getSession(props.username(), props.host(), props.port());
                session.setPassword(props.password());

                // Set up authentication (password or key-based)

                // Avoid strict host key checking
                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);

                session.connect();

                // Setup for running the 'ifconfig' command
                String command = "source /opt/ros/iron/setup.bash && ros2 topic echo /chatter";
                try {
                    ChannelExec channel = (ChannelExec) session.openChannel("exec");
                    channel.setCommand(command);

                    // Get the input stream from the channel
                    try (InputStream in = channel.getInputStream()) {
                        // Execute the command
                        channel.connect();

                        int connectionTimeout = 5000;

                        // Read the output of the command
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        long startTime = System.currentTimeMillis();

                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            System.out.println(line.split("\\s").length);
                            long currentTime = System.currentTimeMillis();
                            if (currentTime - startTime >= connectionTimeout) {
                                break;
                            }
                        }
                    }

                    // return true;
                    didIFailOrDidIsucceedDotCom.set(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    didIFailOrDidIsucceedDotCom.set(false);
                } finally {
                    // Disconnect the session
                    if (session != null && session.isConnected()) {
                        session.disconnect();
                    }
                    System.out.println("We reached the end of the line of the connection.");
                }
            } catch (JSchException e) {
                e.printStackTrace();
                didIFailOrDidIsucceedDotCom.set(false);
            }
            return didIFailOrDidIsucceedDotCom;
        });
    }
}
