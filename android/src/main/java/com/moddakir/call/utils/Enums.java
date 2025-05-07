package com.moddakir.call.utils;

public class Enums {

    public enum PackagesTypeEnum {
        New {
            public String toString() {
                return "new";
            }
        },
        Current {
            public String toString() {
                return "current";
            }
        },
        Consumed {
            public String toString() {
                return "consumed";
            }
        },
        Expire {
            public String toString() {
                return "expired";
            }
        },
        Freezed {
            public String toString() {
                return "Freezed";
            }
        },
        Refunded {
            public String toString() {
                return "Refunded";
            }
        },

    }



}

