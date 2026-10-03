package com.example.rcksuporte05.rcksistemas.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public abstract class Mask {

    public enum MaskType {
        CNPJ("##.###.###/####-##"), CPF("###.###.###-##"), CEP("#####-###"), TEL("(##) #########"), NUMBER("##.##");

        String mask;

        MaskType(String s) {
            mask = s;
        }

        public String getMask() {
            return mask;
        }
    }


    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[ ]", "").replaceAll("[)]", "");
    }

    public static String unmaskNUmber(String s) {
        return s.replaceAll("[,]", ".").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[ ]", "").replaceAll("[)]", "");
    }
    public static String mask(MaskType type, String s) {
        String result = s;

        if (!s.contains(".") && !type.getMask().equalsIgnoreCase("##.##")) {
            String str = Mask.unmask(s.toString());
            result = "";

            int i = 0;
            for (char m : type.getMask().toCharArray()) {
                if (m != '#') {
                    result += m;
                    continue;
                }
                try {
                    result += str.charAt(i);
                } catch (Exception e) {
                    break;
                }
                i++;
            }
        } else {
            result = "";
            String str = Mask.unmaskNUmber(s.toString());
            int i = 0;
            for (char m : type.getMask().toCharArray()) {
                if (m != '#') {
                    result += m;
                    continue;
                }
                try {
                    result += str.charAt(i);
                } catch (Exception e) {
                    break;
                }
                i++;
            }
        }

        return result;
    }


    public static TextWatcher insert(final MaskType type, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (isUpdating){
                    isUpdating = false;
                    old = s.toString();
                    return;
                }

                if (!s.toString().isEmpty() && (s.toString().length() > old.length())) {
                    String str;
                    if ( !type.getMask().equalsIgnoreCase("##.##") ){
                        str = Mask.unmask(s.toString());
                    } else {
                        str = Mask.unmaskNUmber(s.toString());
                    }
                    String mask = "";

                    int i = 0;
                    for (char m : type.getMask().toCharArray()) {
                        if (m != '#') {
                            mask += m;
                            continue;
                        }
                        try {
                            mask += str.charAt(i);
                        } catch (Exception e) {
                            break;
                        }
                        i++;
                    }
                    isUpdating = true;
                    ediTxt.setText(mask);
                    ediTxt.setSelection(mask.length());
                }else{
                    old = s.toString();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }
    public static TextWatcher insert(final MaskType type, final EditText ediTxt, final Button btn) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (isUpdating){
                    isUpdating = false;
                    old = s.toString();
                    return;
                }

                if (!s.toString().isEmpty() && (s.toString().length() > old.length())) {
                    String str;
                    if ( !type.getMask().equalsIgnoreCase("##.##") ){
                        str = Mask.unmask(s.toString());
                    } else {
                        str = Mask.unmaskNUmber(s.toString());
                    }
                    String mask = "";

                    int i = 0;
                    for (char m : type.getMask().toCharArray()) {
                        if (m != '#') {
                            mask += m;
                            continue;
                        }
                        try {
                            mask += str.charAt(i);
                        } catch (Exception e) {
                            break;
                        }
                        i++;
                    }
                    isUpdating = true;
                    ediTxt.setText(mask);
                    ediTxt.setSelection(mask.length());
                }else{
                    old = s.toString();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }



}

