package com.google.appinventor.components.runtime.util;

public class Ev3Constants {

    public static class DataFormat {
        public static final byte DATA_PCT = (byte) 16;
        public static final byte DATA_RAW = (byte) 18;
        public static final byte DATA_SI = (byte) 19;
    }

    public static class DirectCommandType {
        public static final byte DIRECT_COMMAND_NO_REPLY = Byte.MIN_VALUE;
        public static final byte DIRECT_COMMAND_REPLY = (byte) 0;
    }

    public static class DirectReplyType {
        public static final byte DIRECT_REPLY = (byte) 2;
        public static final byte DIRECT_REPLY_ERROR = (byte) 4;
    }

    public static class FontType {
        public static final byte LARGE_FONT = (byte) 2;
        public static final byte NORMAL_FONT = (byte) 0;
        public static final byte SMALL_FONT = (byte) 1;
        public static final byte TINY_FONT = (byte) 3;
    }

    public static class InputDeviceSubcode {
        public static final byte CAL_DEFAULT = (byte) 4;
        public static final byte CAL_MAX = (byte) 8;
        public static final byte CAL_MIN = (byte) 7;
        public static final byte CAL_MINMAX = (byte) 3;
        public static final byte CLR_ALL = (byte) 10;
        public static final byte CLR_CHANGES = (byte) 26;
        public static final byte GET_BUMPS = (byte) 31;
        public static final byte GET_CHANGES = (byte) 25;
        public static final byte GET_CONNECTION = (byte) 12;
        public static final byte GET_FIGURES = (byte) 24;
        public static final byte GET_FORMAT = (byte) 2;
        public static final byte GET_MINMAX = (byte) 30;
        public static final byte GET_MODENAME = (byte) 22;
        public static final byte GET_NAME = (byte) 21;
        public static final byte GET_RAW = (byte) 11;
        public static final byte GET_SYMBOL = (byte) 6;
        public static final byte GET_TYPEMODE = (byte) 5;
        public static final byte READY_PCT = (byte) 27;
        public static final byte READY_RAW = (byte) 28;
        public static final byte READY_SI = (byte) 29;
        public static final byte SETUP = (byte) 9;
        public static final byte SET_RAW = (byte) 23;
        public static final byte STOP_ALL = (byte) 13;
    }

    public static class Opcode {
        public static final byte ADD16 = (byte) 17;
        public static final byte ADD32 = (byte) 18;
        public static final byte ADD8 = (byte) 16;
        public static final byte ADDF = (byte) 19;
        public static final byte AND16 = (byte) 37;
        public static final byte AND32 = (byte) 38;
        public static final byte AND8 = (byte) 36;
        public static final byte ARRAY = (byte) -63;
        public static final byte ARRAY_APPEND = (byte) -60;
        public static final byte ARRAY_READ = (byte) -61;
        public static final byte ARRAY_WRITE = (byte) -62;
        public static final byte BP0 = (byte) -120;
        public static final byte BP1 = (byte) -119;
        public static final byte BP2 = (byte) -118;
        public static final byte BP3 = (byte) -117;
        public static final byte BP_SET = (byte) -116;
        public static final byte CALL = (byte) 9;
        public static final byte COM_GET = (byte) -45;
        public static final byte COM_READ = (byte) -111;
        public static final byte COM_READDATA = (byte) -47;
        public static final byte COM_READY = (byte) -48;
        public static final byte COM_REMOVE = (byte) -42;
        public static final byte COM_SET = (byte) -44;
        public static final byte COM_TEST = (byte) -43;
        public static final byte COM_WRITE = (byte) -110;
        public static final byte COM_WRITEDATA = (byte) -46;
        public static final byte COM_WRITEFILE = (byte) -41;
        public static final byte CP_EQ16 = (byte) 77;
        public static final byte CP_EQ32 = (byte) 78;
        public static final byte CP_EQ8 = (byte) 76;
        public static final byte CP_EQF = (byte) 79;
        public static final byte CP_GT16 = (byte) 73;
        public static final byte CP_GT32 = (byte) 74;
        public static final byte CP_GT8 = (byte) 72;
        public static final byte CP_GTEQ16 = (byte) 89;
        public static final byte CP_GTEQ32 = (byte) 90;
        public static final byte CP_GTEQ8 = (byte) 88;
        public static final byte CP_GTEQF = (byte) 91;
        public static final byte CP_GTF = (byte) 75;
        public static final byte CP_LT16 = (byte) 69;
        public static final byte CP_LT32 = (byte) 70;
        public static final byte CP_LT8 = (byte) 68;
        public static final byte CP_LTEQ16 = (byte) 85;
        public static final byte CP_LTEQ32 = (byte) 86;
        public static final byte CP_LTEQ8 = (byte) 84;
        public static final byte CP_LTEQF = (byte) 87;
        public static final byte CP_LTF = (byte) 71;
        public static final byte CP_NEQ16 = (byte) 81;
        public static final byte CP_NEQ32 = (byte) 82;
        public static final byte CP_NEQ8 = (byte) 80;
        public static final byte CP_NEQF = (byte) 83;
        public static final byte DIV16 = (byte) 29;
        public static final byte DIV32 = (byte) 30;
        public static final byte DIV8 = (byte) 28;
        public static final byte DIVF = (byte) 31;
        public static final byte DO = (byte) 15;
        public static final byte ERROR = (byte) 0;
        public static final byte FILE = (byte) -64;
        public static final byte FILENAME = (byte) -58;
        public static final byte INFO = (byte) 124;
        public static final byte INIT_BYTES = (byte) 47;
        public static final byte INPUT_DEVICE = (byte) -103;
        public static final byte INPUT_DEVICE_LIST = (byte) -104;
        public static final byte INPUT_READ = (byte) -102;
        public static final byte INPUT_READEXT = (byte) -98;
        public static final byte INPUT_READSI = (byte) -99;
        public static final byte INPUT_READY = (byte) -100;
        public static final byte INPUT_SAMPLE = (byte) -105;
        public static final byte INPUT_TEST = (byte) -101;
        public static final byte INPUT_WRITE = (byte) -97;
        public static final byte JR = (byte) 64;
        public static final byte JR_EQ16 = (byte) 109;
        public static final byte JR_EQ32 = (byte) 110;
        public static final byte JR_EQ8 = (byte) 108;
        public static final byte JR_EQF = (byte) 111;
        public static final byte JR_FALSE = (byte) 65;
        public static final byte JR_GT16 = (byte) 105;
        public static final byte JR_GT32 = (byte) 106;
        public static final byte JR_GT8 = (byte) 104;
        public static final byte JR_GTEQ16 = (byte) 121;
        public static final byte JR_GTEQ32 = (byte) 122;
        public static final byte JR_GTEQ8 = (byte) 120;
        public static final byte JR_GTEQF = (byte) 123;
        public static final byte JR_GTF = (byte) 107;
        public static final byte JR_LT16 = (byte) 101;
        public static final byte JR_LT32 = (byte) 102;
        public static final byte JR_LT8 = (byte) 100;
        public static final byte JR_LTEQ16 = (byte) 117;
        public static final byte JR_LTEQ32 = (byte) 118;
        public static final byte JR_LTEQ8 = (byte) 116;
        public static final byte JR_LTEQF = (byte) 119;
        public static final byte JR_LTF = (byte) 103;
        public static final byte JR_NAN = (byte) 67;
        public static final byte JR_NEQ16 = (byte) 113;
        public static final byte JR_NEQ32 = (byte) 114;
        public static final byte JR_NEQ8 = (byte) 112;
        public static final byte JR_NEQF = (byte) 115;
        public static final byte JR_TRUE = (byte) 66;
        public static final byte KEEP_ALIVE = (byte) -112;
        public static final byte LABEL = (byte) 13;
        public static final byte MAILBOX_CLOSE = (byte) -35;
        public static final byte MAILBOX_OPEN = (byte) -40;
        public static final byte MAILBOX_READ = (byte) -38;
        public static final byte MAILBOX_READY = (byte) -36;
        public static final byte MAILBOX_TEST = (byte) -37;
        public static final byte MAILBOX_WRITE = (byte) -39;
        public static final byte MATH = (byte) -115;
        public static final byte MEMORY_READ = Byte.MAX_VALUE;
        public static final byte MEMORY_USAGE = (byte) -59;
        public static final byte MEMORY_WRITE = (byte) 126;
        public static final byte MOVE16_16 = (byte) 53;
        public static final byte MOVE16_32 = (byte) 54;
        public static final byte MOVE16_8 = (byte) 52;
        public static final byte MOVE16_F = (byte) 55;
        public static final byte MOVE32_16 = (byte) 57;
        public static final byte MOVE32_32 = (byte) 58;
        public static final byte MOVE32_8 = (byte) 56;
        public static final byte MOVE32_F = (byte) 59;
        public static final byte MOVE8_16 = (byte) 49;
        public static final byte MOVE8_32 = (byte) 50;
        public static final byte MOVE8_8 = (byte) 48;
        public static final byte MOVE8_F = (byte) 51;
        public static final byte MOVEF_16 = (byte) 61;
        public static final byte MOVEF_32 = (byte) 62;
        public static final byte MOVEF_8 = (byte) 60;
        public static final byte MOVEF_F = (byte) 63;
        public static final byte MUL16 = (byte) 25;
        public static final byte MUL32 = (byte) 26;
        public static final byte MUL8 = (byte) 24;
        public static final byte MULF = (byte) 27;
        public static final byte NOP = (byte) 1;
        public static final byte NOTE_TO_FREQ = (byte) 99;
        public static final byte OBJECT_END = (byte) 10;
        public static final byte OBJECT_START = (byte) 5;
        public static final byte OBJECT_STOP = (byte) 4;
        public static final byte OBJECT_TRIG = (byte) 6;
        public static final byte OBJECT_WAIT = (byte) 7;
        public static final byte OR16 = (byte) 33;
        public static final byte OR32 = (byte) 34;
        public static final byte OR8 = (byte) 32;
        public static final byte OUTPUT_CLR_COUNT = (byte) -78;
        public static final byte OUTPUT_GET_COUNT = (byte) -77;
        public static final byte OUTPUT_GET_TYPE = (byte) -96;
        public static final byte OUTPUT_POLARITY = (byte) -89;
        public static final byte OUTPUT_POSITION = (byte) -85;
        public static final byte OUTPUT_POWER = (byte) -92;
        public static final byte OUTPUT_PRG_STOP = (byte) -76;
        public static final byte OUTPUT_READ = (byte) -88;
        public static final byte OUTPUT_READY = (byte) -86;
        public static final byte OUTPUT_RESET = (byte) -94;
        public static final byte OUTPUT_SET_TYPE = (byte) -95;
        public static final byte OUTPUT_SPEED = (byte) -91;
        public static final byte OUTPUT_START = (byte) -90;
        public static final byte OUTPUT_STEP_POWER = (byte) -84;
        public static final byte OUTPUT_STEP_SPEED = (byte) -82;
        public static final byte OUTPUT_STEP_SYNC = (byte) -80;
        public static final byte OUTPUT_STOP = (byte) -93;
        public static final byte OUTPUT_TEST = (byte) -87;
        public static final byte OUTPUT_TIME_POWER = (byte) -83;
        public static final byte OUTPUT_TIME_SPEED = (byte) -81;
        public static final byte OUTPUT_TIME_SYNC = (byte) -79;
        public static final byte PORT_CNV_INPUT = (byte) 98;
        public static final byte PORT_CNV_OUTPUT = (byte) 97;
        public static final byte PROBE = (byte) 14;
        public static final byte PROGRAM_INFO = (byte) 12;
        public static final byte PROGRAM_START = (byte) 3;
        public static final byte PROGRAM_STOP = (byte) 2;
        public static final byte RANDOM = (byte) -114;
        public static final byte READ16 = (byte) -55;
        public static final byte READ32 = (byte) -54;
        public static final byte READ8 = (byte) -56;
        public static final byte READF = (byte) -53;
        public static final byte RETURN = (byte) 8;
        public static final byte RL16 = (byte) 45;
        public static final byte RL32 = (byte) 46;
        public static final byte RL8 = (byte) 44;
        public static final byte SELECT16 = (byte) 93;
        public static final byte SELECT32 = (byte) 94;
        public static final byte SELECT8 = (byte) 92;
        public static final byte SELECTF = (byte) 95;
        public static final byte SLEEP = (byte) 11;
        public static final byte SOUND = (byte) -108;
        public static final byte SOUND_READY = (byte) -106;
        public static final byte SOUND_TEST = (byte) -107;
        public static final byte STRINGS = (byte) 125;
        public static final byte SUB16 = (byte) 21;
        public static final byte SUB32 = (byte) 22;
        public static final byte SUB8 = (byte) 20;
        public static final byte SUBF = (byte) 23;
        public static final byte SYSTEM = (byte) 96;
        public static final byte TIMER_READ = (byte) -121;
        public static final byte TIMER_READY = (byte) -122;
        public static final byte TIMER_READ_US = (byte) -113;
        public static final byte TIMER_WAIT = (byte) -123;
        public static final byte TST = (byte) -1;
        public static final byte UI_BUTTON = (byte) -125;
        public static final byte UI_DRAW = (byte) -124;
        public static final byte UI_FLUSH = Byte.MIN_VALUE;
        public static final byte UI_READ = (byte) -127;
        public static final byte UI_WRITE = (byte) -126;
        public static final byte WRITE16 = (byte) -51;
        public static final byte WRITE32 = (byte) -50;
        public static final byte WRITE8 = (byte) -52;
        public static final byte WRITEF = (byte) -49;
        public static final byte XOR16 = (byte) 41;
        public static final byte XOR32 = (byte) 42;
        public static final byte XOR8 = (byte) 40;
    }

    public static class SoundSubcode {
        public static final byte BREAK = (byte) 0;
        public static final byte PLAY = (byte) 2;
        public static final byte REPEAT = (byte) 3;
        public static final byte SERVICE = (byte) 4;
        public static final byte TONE = (byte) 1;
    }

    public static class SystemCommand {
        public static final byte BEGIN_DOWNLOAD = (byte) -110;
        public static final byte BEGIN_GETFILE = (byte) -106;
        public static final byte BEGIN_UPLOAD = (byte) -108;
        public static final byte BLUETOOTHPIN = (byte) -97;
        public static final byte CLOSE_FILEHANDLE = (byte) -104;
        public static final byte CONTINUE_DOWNLOAD = (byte) -109;
        public static final byte CONTINUE_GETFILE = (byte) -105;
        public static final byte CONTINUE_LIST_FILES = (byte) -102;
        public static final byte CONTINUE_UPLOAD = (byte) -107;
        public static final byte CREATE_DIR = (byte) -101;
        public static final byte DELETE_FILE = (byte) -100;
        public static final byte ENTERFWUPDATE = (byte) -96;
        public static final byte LIST_FILES = (byte) -103;
        public static final byte LIST_OPEN_HANDLES = (byte) -99;
        public static final byte WRITEMAILBOX = (byte) -98;
    }

    public static class SystemCommandType {
        public static final byte SYSTEM_COMMAND_NO_REPLY = (byte) -127;
        public static final byte SYSTEM_COMMAND_REPLY = (byte) 1;
    }

    public static class SystemReplyStatus {
        public static final byte CORRUPT_FILE = (byte) 3;
        public static final byte END_OF_FILE = (byte) 8;
        public static final byte FILE_EXITS = (byte) 7;
        public static final byte HANDLE_NOT_READY = (byte) 2;
        public static final byte ILLEGAL_CONNECTION = (byte) 12;
        public static final byte ILLEGAL_FILENAME = (byte) 11;
        public static final byte ILLEGAL_PATH = (byte) 6;
        public static final byte NO_HANDLES_AVAILABLE = (byte) 4;
        public static final byte NO_PERMISSION = (byte) 5;
        public static final byte SIZE_ERROR = (byte) 9;
        public static final byte SUCCESS = (byte) 0;
        public static final byte UNKNOWN_ERROR = (byte) 10;
        public static final byte UNKNOWN_HANDLE = (byte) 1;
    }

    public static class SystemReplyType {
        public static final byte SYSTEM_REPLY = (byte) 3;
        public static final byte SYSTEM_REPLY_ERROR = (byte) 5;
    }

    public static class UIButtonSubcode {
        public static final byte FLUSH = (byte) 4;
        public static final byte GET_BACK_BLOCK = (byte) 11;
        public static final byte GET_BUMBED = (byte) 14;
        public static final byte GET_CLICK = (byte) 15;
        public static final byte GET_HORZ = (byte) 7;
        public static final byte GET_VERT = (byte) 8;
        public static final byte LONGPRESS = (byte) 2;
        public static final byte PRESS = (byte) 5;
        public static final byte PRESSED = (byte) 9;
        public static final byte RELEASE = (byte) 6;
        public static final byte SET_BACK_BLOCK = (byte) 10;
        public static final byte SHORTPRESS = (byte) 1;
        public static final byte TESTLONGPRESS = (byte) 13;
        public static final byte TESTSHORTPRESS = (byte) 12;
        public static final byte WAIT_FOR_PRESS = (byte) 3;
    }

    public static class UIDrawSubcode {
        public static final byte BMPFILE = (byte) 28;
        public static final byte BROWSE = (byte) 14;
        public static final byte CIRCLE = (byte) 4;
        public static final byte CLEAN = (byte) 1;
        public static final byte DOTLINE = (byte) 21;
        public static final byte FILLCIRCLE = (byte) 24;
        public static final byte FILLRECT = (byte) 9;
        public static final byte FILLWINDOW = (byte) 19;
        public static final byte GRAPH_DRAW = (byte) 31;
        public static final byte GRAPH_SETUP = (byte) 30;
        public static final byte ICON = (byte) 6;
        public static final byte ICON_QUESTION = (byte) 27;
        public static final byte INVERSERECT = (byte) 16;
        public static final byte KEYBOARD = (byte) 13;
        public static final byte LINE = (byte) 3;
        public static final byte NOTIFICATION = (byte) 11;
        public static final byte PICTURE = (byte) 7;
        public static final byte PIXEL = (byte) 2;
        public static final byte POPUP = (byte) 29;
        public static final byte QUESTION = (byte) 12;
        public static final byte RECT = (byte) 10;
        public static final byte RESTORE = (byte) 26;
        public static final byte SCROLL = (byte) 20;
        public static final byte SELECT_FONT = (byte) 17;
        public static final byte STORE = (byte) 25;
        public static final byte TEXT = (byte) 5;
        public static final byte TEXTBOX = (byte) 32;
        public static final byte TOPLINE = (byte) 18;
        public static final byte UPDATE = (byte) 0;
        public static final byte VALUE = (byte) 8;
        public static final byte VERTBAR = (byte) 15;
        public static final byte VIEW_UNIT = (byte) 23;
        public static final byte VIEW_VALUE = (byte) 22;
    }

    public static class UIReadSubcode {
        public static final byte GET_ADDRESS = (byte) 13;
        public static final byte GET_CODE = (byte) 14;
        public static final byte GET_EVENT = (byte) 4;
        public static final byte GET_FW_BUILD = (byte) 11;
        public static final byte GET_FW_VERS = (byte) 10;
        public static final byte GET_HW_VERS = (byte) 9;
        public static final byte GET_IBATT = (byte) 2;
        public static final byte GET_IINT = (byte) 6;
        public static final byte GET_IMOTOR = (byte) 7;
        public static final byte GET_IP = (byte) 27;
        public static final byte GET_LBATT = (byte) 18;
        public static final byte GET_OS_BUILD = (byte) 12;
        public static final byte GET_OS_VERS = (byte) 3;
        public static final byte GET_POWER = (byte) 29;
        public static final byte GET_SDCARD = (byte) 30;
        public static final byte GET_SHUTDOWN = (byte) 16;
        public static final byte GET_STRING = (byte) 8;
        public static final byte GET_TBATT = (byte) 5;
        public static final byte GET_USBSTICK = (byte) 31;
        public static final byte GET_VBATT = (byte) 1;
        public static final byte GET_VERSION = (byte) 26;
        public static final byte GET_WARNING = (byte) 17;
        public static final byte KEY = (byte) 15;
        public static final byte TEXTBOX_READ = (byte) 21;
    }

    public static class UIWriteSubcode {
        public static final byte ADDRESS = (byte) 13;
        public static final byte CODE = (byte) 14;
        public static final byte DOWNLOAD_END = (byte) 15;
        public static final byte FLOATVALUE = (byte) 2;
        public static final byte GRAPH_SAMPLE = (byte) 30;
        public static final byte INIT_RUN = (byte) 25;
        public static final byte LED = (byte) 27;
        public static final byte POWER = (byte) 29;
        public static final byte PUT_STRING = (byte) 8;
        public static final byte SCREEN_BLOCK = (byte) 16;
        public static final byte SET_BUSY = (byte) 22;
        public static final byte SET_TESTPIN = (byte) 24;
        public static final byte STAMP = (byte) 3;
        public static final byte TERMINAL = (byte) 31;
        public static final byte TEXTBOX_APPEND = (byte) 21;
        public static final byte UPDATE_RUN = (byte) 26;
        public static final byte VALUE16 = (byte) 10;
        public static final byte VALUE32 = (byte) 11;
        public static final byte VALUE8 = (byte) 9;
        public static final byte VALUEF = (byte) 12;
        public static final byte WRITE_FLUSH = (byte) 1;
    }
}
