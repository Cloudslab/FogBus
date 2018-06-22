package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.collect.Lists;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.AsyncCallbackPair;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.GameInstance;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.PlayerListDelta;
import com.google.appinventor.components.runtime.util.WebServiceUtil;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SimpleObject
@DesignerComponent(category = ComponentCategory.INTERNAL, description = "Provides a way for applications to communicate with online game servers", iconName = "images/gameClient.png", nonVisible = true, version = 1)
@UsesPermissions(permissionNames = "android.permission.INTERNET, com.google.android.googleapps.permission.GOOGLE_AUTH")
public class GameClient extends AndroidNonvisibleComponent implements Component, OnResumeListener, OnStopListener {
    private static final String COMMAND_ARGUMENTS_KEY = "args";
    private static final String COMMAND_TYPE_KEY = "command";
    private static final String COUNT_KEY = "count";
    private static final String ERROR_RESPONSE_KEY = "e";
    private static final String GAME_ID_KEY = "gid";
    private static final String GET_INSTANCE_LISTS_COMMAND = "getinstancelists";
    private static final String GET_MESSAGES_COMMAND = "messages";
    private static final String INSTANCE_ID_KEY = "iid";
    private static final String INSTANCE_PUBLIC_KEY = "makepublic";
    private static final String INVITED_LIST_KEY = "invited";
    private static final String INVITEE_KEY = "inv";
    private static final String INVITE_COMMAND = "invite";
    private static final String JOINED_LIST_KEY = "joined";
    private static final String JOIN_INSTANCE_COMMAND = "joininstance";
    private static final String LEADER_KEY = "leader";
    private static final String LEAVE_INSTANCE_COMMAND = "leaveinstance";
    private static final String LOG_TAG = "GameClient";
    private static final String MESSAGES_LIST_KEY = "messages";
    private static final String MESSAGE_CONTENT_KEY = "contents";
    private static final String MESSAGE_RECIPIENTS_KEY = "mrec";
    private static final String MESSAGE_SENDER_KEY = "msender";
    private static final String MESSAGE_TIME_KEY = "mtime";
    private static final String NEW_INSTANCE_COMMAND = "newinstance";
    private static final String NEW_MESSAGE_COMMAND = "newmessage";
    private static final String PLAYERS_LIST_KEY = "players";
    private static final String PLAYER_ID_KEY = "pid";
    private static final String PUBLIC_LIST_KEY = "public";
    private static final String SERVER_COMMAND = "servercommand";
    private static final String SERVER_RETURN_VALUE_KEY = "response";
    private static final String SET_LEADER_COMMAND = "setleader";
    private static final String TYPE_KEY = "type";
    private Activity activityContext;
    private Handler androidUIHandler = new Handler();
    private String gameId;
    private GameInstance instance;
    private List<String> invitedInstances;
    private List<String> joinedInstances;
    private List<String> publicInstances;
    private String serviceUrl;
    private String userEmailAddress = "";

    public GameClient(ComponentContainer container) {
        super(container.$form());
        this.activityContext = container.$context();
        this.form.registerForOnResume(this);
        this.form.registerForOnStop(this);
        this.gameId = "";
        this.instance = new GameInstance("");
        this.joinedInstances = Lists.newArrayList();
        this.invitedInstances = Lists.newArrayList();
        this.publicInstances = Lists.newArrayList();
        this.serviceUrl = "http://appinvgameserver.appspot.com";
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The game name for this application. The same game ID can have one or more game instances.")
    public String GameId() {
        return this.gameId;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    public void GameId(String id) {
        this.gameId = id;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The game instance id.  Taken together,the game ID and the instance ID uniquely identify the game.")
    public String InstanceId() {
        return this.instance.getInstanceId();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The set of game instances to which this player has been invited but has not yet joined.  To ensure current values are returned, first invoke GetInstanceLists.")
    public List<String> InvitedInstances() {
        return this.invitedInstances;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The set of game instances in which this player is participating.  To ensure current values are returned, first invoke GetInstanceLists.")
    public List<String> JoinedInstances() {
        return this.joinedInstances;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The game's leader. At any time, each game instance has only one leader, but the leader may change with time.  Initially, the leader is the game instance creator. Application writers determine special properties of the leader. The leader value is updated each time a successful communication is made with the server.")
    public String Leader() {
        return this.instance.getLeader();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The current set of players for this game instance. Each player is designated by an email address, which is a string. The list of players is updated each time a successful communication is made with the game server.")
    public List<String> Players() {
        return this.instance.getPlayers();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The set of game instances that have been marked public. To ensure current values are returned, first invoke {@link #GetInstanceLists}. ")
    public List<String> PublicInstances() {
        return this.publicInstances;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The URL of the game server.")
    public String ServiceUrl() {
        return this.serviceUrl;
    }

    @DesignerProperty(defaultValue = "http://appinvgameserver.appspot.com", editorType = "string")
    public void ServiceURL(String url) {
        if (url.endsWith("/")) {
            this.serviceUrl = url.substring(0, url.length() - 1);
        } else {
            this.serviceUrl = url;
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The email address that is being used as the player id for this game client.   At present, users must set this manually in oder to join a game.  But this property will change in the future so that is set automatically, and users will not be able to change it.")
    public String UserEmailAddress() {
        if (this.userEmailAddress.equals("")) {
            Info("User email address is empty.");
        }
        return this.userEmailAddress;
    }

    @SimpleProperty
    public void UserEmailAddress(String emailAddress) {
        this.userEmailAddress = emailAddress;
        UserEmailAddressSet(emailAddress);
    }

    @SimpleEvent(description = "Indicates that a function call completed.")
    public void FunctionCompleted(final String functionName) {
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                Log.d(GameClient.LOG_TAG, "Request completed: " + functionName);
                EventDispatcher.dispatchEvent(GameClient.this, "FunctionCompleted", functionName);
            }
        });
    }

    public void Initialize() {
        Log.d(LOG_TAG, "Initialize");
        if (this.gameId.equals("")) {
            throw new YailRuntimeError("Game Id must not be empty.", "GameClient Configuration Error.");
        }
    }

    @SimpleEvent(description = "Indicates that a new message has been received.")
    public void GotMessage(final String type, final String sender, final List<Object> contents) {
        Log.d(LOG_TAG, "Got message of type " + type);
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                EventDispatcher.dispatchEvent(GameClient.this, "GotMessage", type, sender, contents);
            }
        });
    }

    @SimpleEvent(description = "Indicates that the InstanceId property has changed as a result of calling MakeNewInstance or SetInstance.")
    public void InstanceIdChanged(final String instanceId) {
        Log.d(LOG_TAG, "Instance id changed to " + instanceId);
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                EventDispatcher.dispatchEvent(GameClient.this, "InstanceIdChanged", instanceId);
            }
        });
    }

    @SimpleEvent(description = "Indicates that a user has been invited to this game instance.")
    public void Invited(final String instanceId) {
        Log.d(LOG_TAG, "Player invited to " + instanceId);
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                EventDispatcher.dispatchEvent(GameClient.this, "Invited", instanceId);
            }
        });
    }

    @SimpleEvent(description = "Indicates that this game has a new leader as specified through SetLeader")
    public void NewLeader(final String playerId) {
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                Log.d(GameClient.LOG_TAG, "Leader change to " + playerId);
                EventDispatcher.dispatchEvent(GameClient.this, "NewLeader", playerId);
            }
        });
    }

    @SimpleEvent(description = "Indicates that a new instance was successfully created after calling MakeNewInstance.")
    public void NewInstanceMade(final String instanceId) {
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                Log.d(GameClient.LOG_TAG, "New instance made: " + instanceId);
                EventDispatcher.dispatchEvent(GameClient.this, "NewInstanceMade", instanceId);
            }
        });
    }

    @SimpleEvent(description = "Indicates that a new player has joined this game instance.")
    public void PlayerJoined(final String playerId) {
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                if (!playerId.equals(GameClient.this.UserEmailAddress())) {
                    Log.d(GameClient.LOG_TAG, "Player joined: " + playerId);
                    EventDispatcher.dispatchEvent(GameClient.this, "PlayerJoined", playerId);
                }
            }
        });
    }

    @SimpleEvent(description = "Indicates that a player has left this game instance.")
    public void PlayerLeft(final String playerId) {
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                Log.d(GameClient.LOG_TAG, "Player left: " + playerId);
                EventDispatcher.dispatchEvent(GameClient.this, "PlayerLeft", playerId);
            }
        });
    }

    @SimpleEvent(description = "Indicates that a server command failed.")
    public void ServerCommandFailure(final String command, final YailList arguments) {
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                Log.d(GameClient.LOG_TAG, "Server command failed: " + command);
                EventDispatcher.dispatchEvent(GameClient.this, "ServerCommandFailure", command, arguments);
            }
        });
    }

    @SimpleEvent(description = "Indicates that a server command returned successfully.")
    public void ServerCommandSuccess(final String command, final List<Object> response) {
        Log.d(LOG_TAG, command + " server command returned.");
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                EventDispatcher.dispatchEvent(GameClient.this, "ServerCommandSuccess", command, response);
            }
        });
    }

    @SimpleEvent(description = "Indicates that the user email address has been set.")
    public void UserEmailAddressSet(final String emailAddress) {
        Log.d(LOG_TAG, "Email address set.");
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                EventDispatcher.dispatchEvent(GameClient.this, "UserEmailAddressSet", emailAddress);
            }
        });
    }

    @SimpleEvent(description = "Indicates that something has occurred which the player should know about.")
    public void Info(final String message) {
        Log.d(LOG_TAG, "Info: " + message);
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                EventDispatcher.dispatchEvent(GameClient.this, "Info", message);
            }
        });
    }

    @SimpleEvent(description = "Indicates that an error occurred while communicating with the web server.")
    public void WebServiceError(final String functionName, final String message) {
        Log.e(LOG_TAG, "WebServiceError: " + message);
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                EventDispatcher.dispatchEvent(GameClient.this, "WebServiceError", functionName, message);
            }
        });
    }

    @SimpleFunction(description = "Updates the InstancesJoined and InstancesInvited lists. This procedure can be called before setting the InstanceId.")
    public void GetInstanceLists() {
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                GameClient.this.postGetInstanceLists();
            }
        });
    }

    private void postGetInstanceLists() {
        AsyncCallbackPair<JSONObject> readMessagesCallback = new AsyncCallbackPair<JSONObject>() {
            public void onSuccess(JSONObject response) {
                GameClient.this.processInstanceLists(response);
                GameClient.this.FunctionCompleted("GetInstanceLists");
            }

            public void onFailure(String message) {
                GameClient.this.WebServiceError("GetInstanceLists", "Failed to get up to date instance lists.");
            }
        };
        postCommandToGameServer(GET_INSTANCE_LISTS_COMMAND, Lists.newArrayList(new BasicNameValuePair(GAME_ID_KEY, GameId()), new BasicNameValuePair(INSTANCE_ID_KEY, InstanceId()), new BasicNameValuePair(PLAYER_ID_KEY, UserEmailAddress())), readMessagesCallback);
    }

    private void processInstanceLists(JSONObject instanceLists) {
        try {
            this.joinedInstances = JsonUtil.getStringListFromJsonArray(instanceLists.getJSONArray(JOINED_LIST_KEY));
            this.publicInstances = JsonUtil.getStringListFromJsonArray(instanceLists.getJSONArray(PUBLIC_LIST_KEY));
            List<String> receivedInstancesInvited = JsonUtil.getStringListFromJsonArray(instanceLists.getJSONArray(INVITED_LIST_KEY));
            if (!receivedInstancesInvited.equals(InvitedInstances())) {
                List<String> oldList = this.invitedInstances;
                this.invitedInstances = receivedInstancesInvited;
                List<String> newInvites = new ArrayList(receivedInstancesInvited);
                newInvites.removeAll(oldList);
                for (String instanceInvited : newInvites) {
                    Invited(instanceInvited);
                }
            }
        } catch (JSONException e) {
            Log.w(LOG_TAG, e);
            Info("Instance lists failed to parse.");
        }
    }

    @SimpleFunction(description = "Retrieves messages of the specified type.")
    public void GetMessages(final String type, final int count) {
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                GameClient.this.postGetMessages(type, count);
            }
        });
    }

    private void postGetMessages(final String requestedType, int count) {
        AsyncCallbackPair<JSONObject> myCallback = new AsyncCallbackPair<JSONObject>() {
            public void onSuccess(JSONObject result) {
                try {
                    int count = result.getInt(GameClient.COUNT_KEY);
                    JSONArray messages = result.getJSONArray("messages");
                    for (int i = 0; i < count; i++) {
                        JSONObject message = messages.getJSONObject(i);
                        String type = message.getString(GameClient.TYPE_KEY);
                        String sender = message.getString(GameClient.MESSAGE_SENDER_KEY);
                        String time = message.getString(GameClient.MESSAGE_TIME_KEY);
                        List<Object> contents = JsonUtil.getListFromJsonArray(message.getJSONArray(GameClient.MESSAGE_CONTENT_KEY));
                        if (requestedType.equals("")) {
                            GameClient.this.instance.putMessageTime(requestedType, time);
                        }
                        GameClient.this.instance.putMessageTime(type, time);
                        GameClient.this.GotMessage(type, sender, contents);
                    }
                } catch (JSONException e) {
                    Log.w(GameClient.LOG_TAG, e);
                    GameClient.this.Info("Failed to parse messages response.");
                }
                GameClient.this.FunctionCompleted("GetMessages");
            }

            public void onFailure(String message) {
                GameClient.this.WebServiceError("GetMessages", message);
            }
        };
        if (InstanceId().equals("")) {
            Info("You must join an instance before attempting to fetch messages.");
            return;
        }
        postCommandToGameServer("messages", Lists.newArrayList(new BasicNameValuePair(GAME_ID_KEY, GameId()), new BasicNameValuePair(INSTANCE_ID_KEY, InstanceId()), new BasicNameValuePair(PLAYER_ID_KEY, UserEmailAddress()), new BasicNameValuePair(COUNT_KEY, Integer.toString(count)), new BasicNameValuePair(MESSAGE_TIME_KEY, this.instance.getMessageTime(requestedType)), new BasicNameValuePair(TYPE_KEY, requestedType)), myCallback);
    }

    @SimpleFunction(description = "Invites a player to this game instance.")
    public void Invite(final String playerEmail) {
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                GameClient.this.postInvite(playerEmail);
            }
        });
    }

    private void postInvite(String inviteeEmail) {
        AsyncCallbackPair<JSONObject> inviteCallback = new AsyncCallbackPair<JSONObject>() {
            public void onSuccess(JSONObject response) {
                try {
                    String invitedPlayer = response.getString(GameClient.INVITEE_KEY);
                    if (invitedPlayer.equals("")) {
                        GameClient.this.Info(invitedPlayer + " was already invited.");
                    } else {
                        GameClient.this.Info("Successfully invited " + invitedPlayer + ".");
                    }
                } catch (JSONException e) {
                    Log.w(GameClient.LOG_TAG, e);
                    GameClient.this.Info("Failed to parse invite player response.");
                }
                GameClient.this.FunctionCompleted("Invite");
            }

            public void onFailure(String message) {
                GameClient.this.WebServiceError("Invite", message);
            }
        };
        if (InstanceId().equals("")) {
            Info("You must have joined an instance before you can invite new players.");
            return;
        }
        postCommandToGameServer(INVITE_COMMAND, Lists.newArrayList(new BasicNameValuePair(GAME_ID_KEY, GameId()), new BasicNameValuePair(INSTANCE_ID_KEY, InstanceId()), new BasicNameValuePair(PLAYER_ID_KEY, UserEmailAddress()), new BasicNameValuePair(INVITEE_KEY, inviteeEmail)), inviteCallback);
    }

    @SimpleFunction(description = "Leaves the current instance.")
    public void LeaveInstance() {
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                GameClient.this.postLeaveInstance();
            }
        });
    }

    private void postLeaveInstance() {
        AsyncCallbackPair<JSONObject> setInstanceCallback = new AsyncCallbackPair<JSONObject>() {
            public void onSuccess(JSONObject response) {
                GameClient.this.SetInstance("");
                GameClient.this.processInstanceLists(response);
                GameClient.this.FunctionCompleted("LeaveInstance");
            }

            public void onFailure(String message) {
                GameClient.this.WebServiceError("LeaveInstance", message);
            }
        };
        postCommandToGameServer(LEAVE_INSTANCE_COMMAND, Lists.newArrayList(new BasicNameValuePair(GAME_ID_KEY, GameId()), new BasicNameValuePair(INSTANCE_ID_KEY, InstanceId()), new BasicNameValuePair(PLAYER_ID_KEY, UserEmailAddress())), setInstanceCallback);
    }

    @SimpleFunction(description = "Asks the server to create a new instance of this game.")
    public void MakeNewInstance(final String instanceId, final boolean makePublic) {
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                GameClient.this.postMakeNewInstance(instanceId, Boolean.valueOf(makePublic));
            }
        });
    }

    private void postMakeNewInstance(String requestedInstanceId, Boolean makePublic) {
        AsyncCallbackPair<JSONObject> makeNewGameCallback = new AsyncCallbackPair<JSONObject>() {
            public void onSuccess(JSONObject response) {
                GameClient.this.processInstanceLists(response);
                GameClient.this.NewInstanceMade(GameClient.this.InstanceId());
                GameClient.this.FunctionCompleted("MakeNewInstance");
            }

            public void onFailure(String message) {
                GameClient.this.WebServiceError("MakeNewInstance", message);
            }
        };
        postCommandToGameServer(NEW_INSTANCE_COMMAND, Lists.newArrayList(new BasicNameValuePair(PLAYER_ID_KEY, UserEmailAddress()), new BasicNameValuePair(GAME_ID_KEY, GameId()), new BasicNameValuePair(INSTANCE_ID_KEY, requestedInstanceId), new BasicNameValuePair(INSTANCE_PUBLIC_KEY, makePublic.toString())), makeNewGameCallback, true);
    }

    @SimpleFunction(description = "Sends a keyed message to all recipients in the recipients list. The message will consist of the contents list.")
    public void SendMessage(final String type, final YailList recipients, final YailList contents) {
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                GameClient.this.postNewMessage(type, recipients, contents);
            }
        });
    }

    private void postNewMessage(String type, YailList recipients, YailList contents) {
        AsyncCallbackPair<JSONObject> myCallback = new AsyncCallbackPair<JSONObject>() {
            public void onSuccess(JSONObject response) {
                GameClient.this.FunctionCompleted("SendMessage");
            }

            public void onFailure(String message) {
                GameClient.this.WebServiceError("SendMessage", message);
            }
        };
        if (InstanceId().equals("")) {
            Info("You must have joined an instance before you can send messages.");
            return;
        }
        postCommandToGameServer(NEW_MESSAGE_COMMAND, Lists.newArrayList(new BasicNameValuePair(GAME_ID_KEY, GameId()), new BasicNameValuePair(INSTANCE_ID_KEY, InstanceId()), new BasicNameValuePair(PLAYER_ID_KEY, UserEmailAddress()), new BasicNameValuePair(TYPE_KEY, type), new BasicNameValuePair(MESSAGE_RECIPIENTS_KEY, recipients.toJSONString()), new BasicNameValuePair(MESSAGE_CONTENT_KEY, contents.toJSONString()), new BasicNameValuePair(MESSAGE_TIME_KEY, this.instance.getMessageTime(type))), myCallback);
    }

    @SimpleFunction(description = "Sends the specified command to the game server.")
    public void ServerCommand(final String command, final YailList arguments) {
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                GameClient.this.postServerCommand(command, arguments);
            }
        });
    }

    private void postServerCommand(final String command, final YailList arguments) {
        AsyncCallbackPair<JSONObject> myCallback = new AsyncCallbackPair<JSONObject>() {
            public void onSuccess(JSONObject result) {
                try {
                    GameClient.this.ServerCommandSuccess(command, JsonUtil.getListFromJsonArray(result.getJSONArray(GameClient.MESSAGE_CONTENT_KEY)));
                } catch (JSONException e) {
                    Log.w(GameClient.LOG_TAG, e);
                    GameClient.this.Info("Server command response failed to parse.");
                }
                GameClient.this.FunctionCompleted("ServerCommand");
            }

            public void onFailure(String message) {
                GameClient.this.ServerCommandFailure(command, arguments);
                GameClient.this.WebServiceError("ServerCommand", message);
            }
        };
        Log.d(LOG_TAG, "Going to post " + command + " with args " + arguments);
        postCommandToGameServer(SERVER_COMMAND, Lists.newArrayList(new BasicNameValuePair(GAME_ID_KEY, GameId()), new BasicNameValuePair(INSTANCE_ID_KEY, InstanceId()), new BasicNameValuePair(PLAYER_ID_KEY, UserEmailAddress()), new BasicNameValuePair(COMMAND_TYPE_KEY, command), new BasicNameValuePair(COMMAND_ARGUMENTS_KEY, arguments.toJSONString())), myCallback);
    }

    @SimpleFunction(description = "Sets InstanceId and joins the specified instance.")
    public void SetInstance(final String instanceId) {
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                if (instanceId.equals("")) {
                    Log.d(GameClient.LOG_TAG, "Instance id set to empty string.");
                    if (!GameClient.this.InstanceId().equals("")) {
                        GameClient.this.instance = new GameInstance("");
                        GameClient.this.InstanceIdChanged("");
                        GameClient.this.FunctionCompleted("SetInstance");
                        return;
                    }
                    return;
                }
                GameClient.this.postSetInstance(instanceId);
            }
        });
    }

    private void postSetInstance(String instanceId) {
        AsyncCallbackPair<JSONObject> setInstanceCallback = new AsyncCallbackPair<JSONObject>() {
            public void onSuccess(JSONObject response) {
                GameClient.this.processInstanceLists(response);
                GameClient.this.FunctionCompleted("SetInstance");
            }

            public void onFailure(String message) {
                GameClient.this.WebServiceError("SetInstance", message);
            }
        };
        postCommandToGameServer(JOIN_INSTANCE_COMMAND, Lists.newArrayList(new BasicNameValuePair(GAME_ID_KEY, GameId()), new BasicNameValuePair(INSTANCE_ID_KEY, instanceId), new BasicNameValuePair(PLAYER_ID_KEY, UserEmailAddress())), setInstanceCallback, true);
    }

    @SimpleFunction(description = "Tells the server to set the leader to playerId. Only the current leader may successfully set a new leader.")
    public void SetLeader(final String playerEmail) {
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                GameClient.this.postSetLeader(playerEmail);
            }
        });
    }

    private void postSetLeader(String newLeader) {
        AsyncCallbackPair<JSONObject> setLeaderCallback = new AsyncCallbackPair<JSONObject>() {
            public void onSuccess(JSONObject response) {
                GameClient.this.FunctionCompleted("SetLeader");
            }

            public void onFailure(String message) {
                GameClient.this.WebServiceError("SetLeader", message);
            }
        };
        if (InstanceId().equals("")) {
            Info("You must join an instance before attempting to set a leader.");
            return;
        }
        postCommandToGameServer(SET_LEADER_COMMAND, Lists.newArrayList(new BasicNameValuePair(GAME_ID_KEY, GameId()), new BasicNameValuePair(INSTANCE_ID_KEY, InstanceId()), new BasicNameValuePair(PLAYER_ID_KEY, UserEmailAddress()), new BasicNameValuePair(LEADER_KEY, newLeader)), setLeaderCallback);
    }

    public void onResume() {
        Log.d(LOG_TAG, "Activity Resumed.");
    }

    public void onStop() {
        Log.d(LOG_TAG, "Activity Stopped.");
    }

    private void postCommandToGameServer(String commandName, List<NameValuePair> params, AsyncCallbackPair<JSONObject> callback) {
        postCommandToGameServer(commandName, params, callback, false);
    }

    private void postCommandToGameServer(String commandName, List<NameValuePair> params, AsyncCallbackPair<JSONObject> callback, boolean allowInstanceIdChange) {
        final String str = commandName;
        final AsyncCallbackPair<JSONObject> asyncCallbackPair = callback;
        final boolean z = allowInstanceIdChange;
        final List<NameValuePair> list = params;
        WebServiceUtil.getInstance().postCommandReturningObject(ServiceUrl(), commandName, params, new AsyncCallbackPair<JSONObject>() {
            public void onSuccess(JSONObject responseObject) {
                Log.d(GameClient.LOG_TAG, "Received response for " + str + ": " + responseObject.toString());
                try {
                    if (responseObject.getBoolean(GameClient.ERROR_RESPONSE_KEY)) {
                        asyncCallbackPair.onFailure(responseObject.getString(GameClient.SERVER_RETURN_VALUE_KEY));
                        return;
                    }
                    String responseGameId = responseObject.getString(GameClient.GAME_ID_KEY);
                    if (responseGameId.equals(GameClient.this.GameId())) {
                        String responseInstanceId = responseObject.getString(GameClient.INSTANCE_ID_KEY);
                        if (responseInstanceId.equals("")) {
                            asyncCallbackPair.onSuccess(responseObject.getJSONObject(GameClient.SERVER_RETURN_VALUE_KEY));
                            return;
                        }
                        if (responseInstanceId.equals(GameClient.this.InstanceId())) {
                            GameClient.this.updateInstanceInfo(responseObject);
                        } else if (z || GameClient.this.InstanceId().equals("")) {
                            GameClient.this.instance = new GameInstance(responseInstanceId);
                            GameClient.this.updateInstanceInfo(responseObject);
                            GameClient.this.InstanceIdChanged(responseInstanceId);
                        } else {
                            GameClient.this.Info("Ignored server response to " + str + " for incorrect instance " + responseInstanceId + ".");
                            return;
                        }
                        asyncCallbackPair.onSuccess(responseObject.getJSONObject(GameClient.SERVER_RETURN_VALUE_KEY));
                        return;
                    }
                    GameClient.this.Info("Incorrect game id in response: + " + responseGameId + ".");
                } catch (JSONException e) {
                    Log.w(GameClient.LOG_TAG, e);
                    asyncCallbackPair.onFailure("Failed to parse JSON response to command " + str);
                }
            }

            public void onFailure(String failureMessage) {
                Log.d(GameClient.LOG_TAG, "Posting to server failed for " + str + " with arguments " + list + "\n Failure message: " + failureMessage);
                asyncCallbackPair.onFailure(failureMessage);
            }
        });
    }

    private void updateInstanceInfo(JSONObject responseObject) throws JSONException {
        boolean newLeader = false;
        String leader = responseObject.getString(LEADER_KEY);
        List<String> receivedPlayers = JsonUtil.getStringListFromJsonArray(responseObject.getJSONArray(PLAYERS_LIST_KEY));
        if (!Leader().equals(leader)) {
            this.instance.setLeader(leader);
            newLeader = true;
        }
        PlayerListDelta playersDelta = this.instance.setPlayers(receivedPlayers);
        if (playersDelta != PlayerListDelta.NO_CHANGE) {
            for (String player : playersDelta.getPlayersRemoved()) {
                PlayerLeft(player);
            }
            for (String player2 : playersDelta.getPlayersAdded()) {
                PlayerJoined(player2);
            }
        }
        if (newLeader) {
            NewLeader(Leader());
        }
    }
}
