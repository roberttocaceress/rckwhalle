package com.example.rcksuporte05.rcksistemas.util;

import android.content.res.Resources;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class ColorTemplateApp {
    public static final int COLOR_NONE = 0x00112233;

    /**
     * this "color" is used for the Legend creation and indicates that the next
     * form should be skipped
     */
    public static final int COLOR_SKIP = 0x00112234;

    /**
     * THE COLOR THEMES ARE PREDEFINED (predefined color integer arrays), FEEL
     * FREE TO CREATE YOUR OWN WITH AS MANY DIFFERENT COLORS AS YOU WANT
     */

   /*
    public static final int[] SEMANA_COLORS_APP_1 = {
            Color.rgb(49, 89, 85)
    };
    public static final int[] SEMANA_COLORS_APP_2 = {

            Color.rgb(49, 89, 85)  , Color.rgb(234, 191, 150)
    };
    public static final int[] SEMANA_COLORS_APP_3 = {
            Color.rgb(49, 89, 85)  , Color.rgb(234, 191, 150), Color.rgb(90, 95, 49),  //3,  //3
                                                   //5

    };
    public static final int[] SEMANA_COLORS_APP_4 = {
            Color.rgb(49, 89, 85)  , Color.rgb(234, 191, 150), Color.rgb(90, 95, 49),  //3
            Color.rgb(234, 209, 150)

    };

    */

    // Cores de Início (Mais claras/vibrantes no
    //  public static final int[] GRADIENT_START_2 = {
    //            Color.rgb(0, 191, 165),   // Teal Claro
    //            Color.rgb(255, 183, 77),  // Laranja Claro
    //    };
    //
    //    // Cores de Fim (Mais escuras/profundas na base)
    //    public static final int[] GRADIENT_END_2 = {
    //            Color.rgb(0, 121, 107),   // Teal Escuro
    //            Color.rgb(245, 124, 0),   // Laranja Profundo
    //    };

    public static final int[] PIE_COLORS_9 = {
            Color.rgb(0, 191, 165),   // Teal (Vibrante)
            Color.rgb(255, 183, 77),  // Laranja (Vibrante)
            Color.rgb(100, 181, 246), // Azul (Vibrante)
            Color.rgb(220, 231, 117), // Verde Lima (Vibrante)
            Color.rgb(186, 104, 200), // Roxo (Vibrante)
            Color.rgb(240, 98, 146),  // Rosa (Vibrante)
            Color.rgb(255, 213, 79),  // Amarelo Âmbar (Vibrante)
            Color.rgb(77, 208, 225),  // Ciano (Vibrante)
            Color.rgb(121, 134, 203)   // Índigo (Vibrante)
    };

    public static final int[] PIE_COLORS_6 = {
            Color.rgb(0, 191, 165),   // Teal
            Color.rgb(255, 183, 77),  // Laranja
            Color.rgb(100, 181, 246), // Azul
            Color.rgb(220, 231, 117), // Verde Lima
            Color.rgb(186, 104, 200), // Roxo
            Color.rgb(158, 158, 158)  // Cinza (para "Outros")
    };


    public static final int[] PIE_COLORS_6_RANK = {
            Color.rgb(158, 158, 158),  // Cinza (para "Outros")
            Color.rgb(186, 104, 200), // Roxo
            Color.rgb(220, 231, 117), // Verde Lima
            Color.rgb(100, 181, 246), // Azul
            Color.rgb(255, 183, 77),  // Laranja
            Color.rgb(0, 191, 165),   // Teal

    };

    public static final int[] PIE_COLORS_11 = {
            Color.rgb(0, 191, 165),   // Teal
            Color.rgb(255, 183, 77),  // Laranja
            Color.rgb(100, 181, 246), // Azul
            Color.rgb(220, 231, 117), // Verde Lima
            Color.rgb(186, 104, 200), // Roxo
            Color.rgb(240, 98, 146),  // Rosa
            Color.rgb(255, 213, 79),  // Amarelo Âmbar
            Color.rgb(77, 208, 225),  // Ciano
            Color.rgb(63, 81, 181),   // Índigo
            Color.rgb(255, 87, 34),   // Laranja Escuro
            Color.rgb(158, 158, 158)  // Cinza (para "Outros")
    };

    public static final int[] PIE_COLORS_11_RANK = {
            Color.rgb(158, 158, 158),  // Cinza (para "Outros")
            Color.rgb(255, 87, 34),
            Color.rgb(63, 81, 181),   // Índigo
            Color.rgb(77, 208, 225),  // Ciano
            Color.rgb(255, 213, 79),  // Amarelo Âmbar
            Color.rgb(240, 98, 146),  // Rosa
            Color.rgb(186, 104, 200), // Roxo
            Color.rgb(220, 231, 117), // Verde Lima
            Color.rgb(100, 181, 246), // Azul
            Color.rgb(255, 183, 77),  // Laranja
            Color.rgb(0, 191, 165),   // Teal
    };



    public static final int[] PIE_COLORS_16 = {
            Color.rgb(0, 191, 165),   // Teal
            Color.rgb(255, 183, 77),  // Laranja
            Color.rgb(100, 181, 246), // Azul
            Color.rgb(220, 231, 117), // Verde Lima
            Color.rgb(186, 104, 200), // Roxo
            Color.rgb(240, 98, 146),  // Rosa
            Color.rgb(255, 213, 79),  // Amarelo Âmbar
            Color.rgb(77, 208, 225),  // Ciano
            Color.rgb(63, 81, 181),   // Índigo
            Color.rgb(255, 87, 34),   // Laranja Escuro
            Color.rgb(139, 195, 74),  // Verde Claro
            Color.rgb(0, 150, 136),   // Teal Escuro
            Color.rgb(255, 152, 0),   // Laranja Forte
            Color.rgb(33, 150, 243),  // Azul Forte
            Color.rgb(156, 39, 176),  // Roxo Forte
            Color.rgb(158, 158, 158)  // Cinza (para "Outros")
    };
    public static final int[] PIE_COLORS_16_RANK = {
            Color.rgb(158, 158, 158),  // Cinza (para "Outros")
            Color.rgb(156, 39, 176),  // Roxo Forte
            Color.rgb(33, 150, 243),  // Azul Forte
            Color.rgb(255, 152, 0),   // Laranja Forte
            Color.rgb(0, 150, 136),   // Teal Escuro
            Color.rgb(139, 195, 74),  // Verde Claro
            Color.rgb(255, 87, 34),   // Laranja Escuro
            Color.rgb(63, 81, 181),   // Índigo
            Color.rgb(77, 208, 225),  // Ciano
            Color.rgb(255, 213, 79),  // Amarelo Âmbar
            Color.rgb(240, 98, 146),  // Rosa
            Color.rgb(186, 104, 200), // Roxo
            Color.rgb(220, 231, 117), // Verde Lima
            Color.rgb(100, 181, 246), // Azul
            Color.rgb(255, 183, 77),  // Laranja
            Color.rgb(0, 191, 165),   // Teal

    };

    public static final int[] PIE_COLORS_21 = {
            Color.rgb(0, 191, 165),   // Teal
            Color.rgb(255, 183, 77),  // Laranja
            Color.rgb(100, 181, 246), // Azul
            Color.rgb(220, 231, 117), // Verde Lima
            Color.rgb(186, 104, 200), // Roxo
            Color.rgb(240, 98, 146),  // Rosa
            Color.rgb(255, 213, 79),  // Amarelo Âmbar
            Color.rgb(77, 208, 225),  // Ciano
            Color.rgb(63, 81, 181),   // Índigo
            Color.rgb(255, 87, 34),   // Laranja Escuro
            Color.rgb(139, 195, 74),  // Verde Claro
            Color.rgb(0, 150, 136),   // Teal Escuro
            Color.rgb(255, 152, 0),   // Laranja Forte
            Color.rgb(33, 150, 243),  // Azul Forte
            Color.rgb(156, 39, 176),  // Roxo Forte
            Color.rgb(233, 30, 99),   // Rosa Forte
            Color.rgb(255, 193, 7),    // Amarelo Forte
            Color.rgb(0, 188, 212),   // Ciano Novo
            Color.rgb(121, 85, 72),   // Marrom
            Color.rgb(46, 125, 50),   // Verde Floresta
            Color.rgb(158, 158, 158)  // Cinza (para "Outros")
    };


    public static final int[] PIE_COLORS_21_RANK = {
            Color.rgb(158, 158, 158),  // Cinza (para "Outros")
            Color.rgb(46, 125, 50),   // Verde Floresta
            Color.rgb(121, 85, 72),   // Marrom
            Color.rgb(0, 188, 212),   // Ciano Novo
            Color.rgb(255, 193, 7),    // Amarelo Forte
            Color.rgb(233, 30, 99),   // Rosa Forte
            Color.rgb(156, 39, 176),  // Roxo Forte
            Color.rgb(33, 150, 243),  // Azul Forte
            Color.rgb(255, 152, 0),   // Laranja Forte
            Color.rgb(0, 150, 136),   // Teal Escuro
            Color.rgb(139, 195, 74),  // Verde Claro
            Color.rgb(255, 87, 34),   // Laranja Escuro
            Color.rgb(63, 81, 181),   // Índigo
            Color.rgb(77, 208, 225),  // Ciano
            Color.rgb(255, 213, 79),  // Amarelo Âmbar
            Color.rgb(240, 98, 146),  // Rosa
            Color.rgb(186, 104, 200), // Roxo
            Color.rgb(220, 231, 117), // Verde Lima
            Color.rgb(100, 181, 246), // Azul
            Color.rgb(255, 183, 77),  // Laranja
            Color.rgb(0, 191, 165),   // Teal
    };

    public static final int[] GRADIENT_START_1 = {
            Color.rgb(0, 191, 165),   // Teal Claro
    };

    // Cores de Fim (Mais escuras/profundas na base)
    public static final int[] GRADIENT_END_1 = {
            Color.rgb(0, 121, 107),   // Teal Escuro
    };

    public static final int[] GRADIENT_START_2 = {
            Color.rgb(0, 191, 165),   // Teal Claro
            Color.rgb(255, 183, 77),  // Laranja Claro
    };

    // Cores de Fim (Mais escuras/profundas na base)
    public static final int[] GRADIENT_END_2 = {
            Color.rgb(0, 121, 107),   // Teal Escuro
            Color.rgb(245, 124, 0),   // Laranja Profundo
    };

    public static final int[] GRADIENT_START_3 = {
            Color.rgb(0, 191, 165),   // Teal Claro
            Color.rgb(255, 183, 77),  // Laranja Claro
            Color.rgb(100, 181, 246), // Azul Claro
    };

    // Cores de Fim (Mais escuras/profundas na base)
    public static final int[] GRADIENT_END_3 = {
            Color.rgb(0, 121, 107),   // Teal Escuro
            Color.rgb(245, 124, 0),   // Laranja Profundo
            Color.rgb(25, 118, 210),  // Azul Forte
    };

    public static final int[] GRADIENT_START_4 = {
            Color.rgb(0, 191, 165),   // Teal Claro
            Color.rgb(255, 183, 77),  // Laranja Claro
            Color.rgb(100, 181, 246), // Azul Claro
            Color.rgb(220, 231, 117), // Verde Lima Claro
    };

    // Cores de Fim (Mais escuras/profundas na base)
    public static final int[] GRADIENT_END_4 = {
            Color.rgb(0, 121, 107),   // Teal Escuro
            Color.rgb(245, 124, 0),   // Laranja Profundo
            Color.rgb(25, 118, 210),  // Azul Forte
            Color.rgb(139, 195, 74),  // Verde Lima Sólido
    };
    public static final int[] GRADIENT_START_5 = {
            Color.rgb(0, 191, 165),   // Teal Claro
            Color.rgb(255, 183, 77),  // Laranja Claro
            Color.rgb(100, 181, 246), // Azul Claro
            Color.rgb(220, 231, 117), // Verde Lima Claro
            Color.rgb(186, 104, 200), // Roxo Médio
    };

    // Cores de Fim (Mais escuras/profundas na base)
    public static final int[] GRADIENT_END_5 = {
            Color.rgb(0, 121, 107),   // Teal Escuro
            Color.rgb(245, 124, 0),   // Laranja Profundo
            Color.rgb(25, 118, 210),  // Azul Forte
            Color.rgb(139, 195, 74),  // Verde Lima Sólido
            Color.rgb(123, 31, 162),  // Roxo Profundo
    };
    public static final int[] GRADIENT_START_6 = {
            Color.rgb(0, 191, 165),   // Teal Claro
            Color.rgb(255, 183, 77),  // Laranja Claro
            Color.rgb(100, 181, 246), // Azul Claro
            Color.rgb(220, 231, 117), // Verde Lima Claro
            Color.rgb(186, 104, 200), // Roxo Médio
            Color.rgb(240, 98, 146)   // Rosa Suave
    };

    // Cores de Fim (Mais escuras/profundas na base)
    public static final int[] GRADIENT_END_6 = {
            Color.rgb(0, 121, 107),   // Teal Escuro
            Color.rgb(245, 124, 0),   // Laranja Profundo
            Color.rgb(25, 118, 210),  // Azul Forte
            Color.rgb(139, 195, 74),  // Verde Lima Sólido
            Color.rgb(123, 31, 162),  // Roxo Profundo
            Color.rgb(194, 24, 91)    // Rosa Forte
    };
    public static final int[] SEMANA_COLORS_APP_1 = {
            Color.rgb(0, 150, 136),   // Teal (Profissional/Sóbrio)
    };
    public static final int[] SEMANA_COLORS_APP_2 = {
            Color.rgb(0, 150, 136),   // Teal (Profissional/Sóbrio)
            Color.rgb(255, 152, 0),   // Laranja (Destaque/Atenção)
    };
    public static final int[] SEMANA_COLORS_APP_3 = {
            Color.rgb(0, 150, 136),   // Teal (Profissional/Sóbrio)
            Color.rgb(255, 152, 0),   // Laranja (Destaque/Atenção)
            Color.rgb(33, 150, 243),  // Azul (Confiança/Faturamento)
    };
    public static final int[] SEMANA_COLORS_APP_4 = {
            Color.rgb(0, 150, 136),   // Teal (Profissional/Sóbrio)
            Color.rgb(255, 152, 0),   // Laranja (Destaque/Atenção)
            Color.rgb(33, 150, 243),  // Azul (Confiança/Faturamento)
            Color.rgb(139, 195, 74),  // Verde Lima (Positivo/Crescimento)
    };
    public static final int[] SEMANA_COLORS_APP_5 = {
            Color.rgb(0, 150, 136),   // Teal (Profissional/Sóbrio)
            Color.rgb(255, 152, 0),   // Laranja (Destaque/Atenção)
            Color.rgb(33, 150, 243),  // Azul (Confiança/Faturamento)
            Color.rgb(139, 195, 74),  // Verde Lima (Positivo/Crescimento)
            Color.rgb(156, 39, 176)   // Roxo (Diferenciação)
    };
    public static final int[] SEMANA_COLORS_APP_6 = {
            Color.rgb(0, 150, 136),   // Teal (Profissional/Sóbrio)
            Color.rgb(255, 152, 0),   // Laranja (Destaque/Atenção)
            Color.rgb(33, 150, 243),  // Azul (Confiança/Faturamento)
            Color.rgb(139, 195, 74),  // Verde Lima (Positivo/Crescimento)
            Color.rgb(156, 39, 176),  // Roxo (Diferenciação)
            Color.rgb(233, 30, 99)    // Rosa (Metas/Urgência)
    };
    public static final int[] SEMANA_COLORS_APP_7 = {
            Color.rgb(0, 150, 136),   // Teal (Profissional/Sóbrio)
            Color.rgb(255, 152, 0),   // Laranja (Destaque/Atenção)
            Color.rgb(33, 150, 243),  // Azul (Confiança/Faturamento)
            Color.rgb(139, 195, 74),  // Verde Lima (Positivo/Crescimento)
            Color.rgb(156, 39, 176),  // Roxo (Diferenciação)
            Color.rgb(233, 30, 99),   // Rosa (Metas/Urgência)
            Color.rgb(255, 193, 7)    // Amarelo Âmbar (Destaque Secundário)
    };
    public static final int[] SEMANA_COLORS_APP_8 = {
            Color.rgb(0, 150, 136),   // Teal (Profissional/Sóbrio)
            Color.rgb(255, 152, 0),   // Laranja (Destaque/Atenção)
            Color.rgb(33, 150, 243),  // Azul (Confiança/Faturamento)
            Color.rgb(139, 195, 74),  // Verde Lima (Positivo/Crescimento)
            Color.rgb(156, 39, 176),  // Roxo (Diferenciação)
            Color.rgb(233, 30, 99),   // Rosa (Metas/Urgência)
            Color.rgb(255, 193, 7),   // Amarelo Âmbar (Destaque Secundário)
            Color.rgb(0, 188, 212)    // Ciano (Novo: Tecnologia/Clareza)
    };
    /*
    public static final int[] SEMANA_COLORS_APP_5 = {
            Color.rgb(49, 89, 85)  , Color.rgb(234, 191, 150), Color.rgb(90, 95, 49),  //3
            Color.rgb(234, 209, 150), Color.rgb(49, 74, 95)                                              //5

    };

     */
    /*
    public static final int[] SEMANA_COLORS_APP_6 = {
            Color.rgb(49, 89, 85)  , Color.rgb(234, 191, 150), Color.rgb(90, 95, 49),  //3
            Color.rgb(234, 209, 150), Color.rgb(49, 74, 95),  Color.rgb(64, 234, 191)                                               //5

    };

     */
    /*
    public static final int[] SEMANA_COLORS_APP_7 = {
            Color.rgb(49, 89, 85)  , Color.rgb(234, 191, 150), Color.rgb(96, 49, 89),  //3
            Color.rgb(96, 234, 191), Color.rgb(49, 74, 95) , Color.rgb(64, 234, 191) ,
            Color.rgb(32, 49, 89)

    };

     */
/*
    public static final int[] SEMANA_COLORS_APP_8 = {
            Color.rgb(49, 89, 85)  , Color.rgb(234, 191, 150), Color.rgb(96, 49, 89),  //3
            Color.rgb(96, 234, 191), Color.rgb(49, 74, 95) , Color.rgb(64, 234, 191) ,
            Color.rgb(32, 49, 89) ,Color.rgb(49, 79, 95)

    };
    */

    public static final int[] SEMANA_COLORS_APP_9 = {
            Color.rgb(49, 89, 85)  , Color.rgb(234, 191, 150), Color.rgb(96, 49, 89),  //3
            Color.rgb(96, 234, 191), Color.rgb(49, 74, 95) , Color.rgb(64, 234, 191) ,
            Color.rgb(32, 49, 89) ,Color.rgb(49, 79, 95), Color.rgb(49, 60, 95)
            //49, 60, 95

    };
    public static final int[] SEMANA_COLORS_APP_10 = {
            Color.rgb(49, 89, 85)  , Color.rgb(234, 191, 150), Color.rgb(96, 49, 89),  //3
            Color.rgb(96, 234, 191), Color.rgb(49, 74, 95) , Color.rgb(64, 234, 191) ,
            Color.rgb(32, 49, 89) ,Color.rgb(49, 79, 95), Color.rgb(49, 60, 95),
            Color.rgb(32, 49, 89)
            //49, 60, 95

    };
    public static final int[] SEMANA_COLORS_APP_11 = {
            Color.rgb(49, 89, 85)  , Color.rgb(234, 191, 150), Color.rgb(96, 49, 89),  //3
            Color.rgb(96, 234, 191), Color.rgb(49, 74, 95) , Color.rgb(64, 234, 191) ,
            Color.rgb(32, 49, 89) ,Color.rgb(49, 79, 95), Color.rgb(49, 60, 95),
            Color.rgb(32, 49, 89) ,Color.rgb(61, 49, 95)
            //49, 60, 95

    };

    public static final int[] SEMANA_COLORS_APP_12 = {
            Color.rgb(49, 89, 85)  , Color.rgb(234, 191, 150), Color.rgb(96, 49, 89),  //3
            Color.rgb(96, 234, 191), Color.rgb(49, 74, 95)   , Color.rgb(64, 234, 191) ,
            Color.rgb(32, 49, 89)  ,Color.rgb(49, 79, 95)    , Color.rgb(49, 60, 95),
            Color.rgb(32, 49, 89)  ,Color.rgb(61, 49, 95)    , Color.rgb(87, 95, 49)
            //49, 60, 95

    };
    public static final int[] SEMANA_COLORS_APP_13 = {
            Color.rgb(49, 89, 85)  , Color.rgb(234, 191, 150), Color.rgb(96, 49, 89),  //3
            Color.rgb(96, 234, 191), Color.rgb(49, 74, 95)   , Color.rgb(64, 234, 191) ,
            Color.rgb(32, 49, 89)  ,Color.rgb(49, 79, 95)    , Color.rgb(49, 60, 95),
            Color.rgb(32, 49, 89)  ,Color.rgb(61, 49, 95)    , Color.rgb(87, 95, 49),
            Color.rgb(73, 49, 95)  ,
            //49, 60, 95

    };
    public static final int[] SEMANA_COLORS_APP_14 = {
            Color.rgb(49, 89, 85)  , Color.rgb(234, 191, 150), Color.rgb(96, 49, 89),  //3
            Color.rgb(96, 234, 191), Color.rgb(49, 74, 95)   , Color.rgb(64, 234, 191) ,
            Color.rgb(32, 49, 89)  ,Color.rgb(49, 79, 95)    , Color.rgb(49, 60, 95),
            Color.rgb(32, 49, 89)  ,Color.rgb(61, 49, 95)    , Color.rgb(87, 95, 49),
            Color.rgb(73, 49, 95)  ,Color.rgb(95, 85, 49)  ,
            //49, 60, 95

    };
    public static final int[] SEMANA_COLORS_APP_15 = {
            Color.rgb(49, 89, 85)  , Color.rgb(234, 191, 150), Color.rgb(96, 49, 89),  //3
            Color.rgb(96, 234, 191), Color.rgb(49, 74, 95)   , Color.rgb(64, 234, 191) ,
            Color.rgb(32, 49, 89)  ,Color.rgb(49, 79, 95)    , Color.rgb(49, 60, 95),
            Color.rgb(32, 49, 89)  ,Color.rgb(61, 49, 95)    , Color.rgb(87, 95, 49),
            Color.rgb(73, 49, 95)  ,Color.rgb(95, 85, 49)    , Color.rgb(95, 72, 49)
            //49, 60, 95

    };
    public static final int[] LIBERTY_COLORS = {
            Color.rgb(207, 248, 246), Color.rgb(148, 212, 212), Color.rgb(136, 180, 187),
            Color.rgb(118, 174, 175), Color.rgb(42, 109, 130)
    };
    public static final int[] JOYFUL_COLORS = {
            Color.rgb(217, 80, 138), Color.rgb(254, 149, 7), Color.rgb(254, 247, 120),
            Color.rgb(106, 167, 134), Color.rgb(53, 194, 209)
    };
    public static final int[] PASTEL_COLORS = {
            Color.rgb(64, 89, 128)  , Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),  //3
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80)  , Color.rgb(216, 180, 237),  //6
            Color.rgb(199, 143, 141), Color.rgb(53, 194, 209)
    };

    public static final int[] PASTEL_COLORS_5 = {
            Color.rgb(64, 89, 128)  , Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),  //3
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80)                                               //5

    };

    public static final int[] PASTEL_COLORS_6 = {
            Color.rgb(64, 89, 128)  , Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),  //3
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80)  , Color.rgb(216, 180, 237),  //6

    };



    public static final int[] COLORFUL_COLORS = {
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53)
    };
    public static final int[] VORDIPLOM_COLORS = {
            Color.rgb(192, 255, 140), Color.rgb(255, 247, 140), Color.rgb(255, 208, 140),
            Color.rgb(140, 234, 255), Color.rgb(255, 140, 157)
    };
    public static final int[] MATERIAL_COLORS = {
            rgb("#2ecc71"), rgb("#f1c40f"), rgb("#e74c3c"), rgb("#3498db")
    };

    /**
     * Converts the given hex-color-string to rgb.
     *
     * @param hex
     * @return
     */
    public static int rgb(String hex) {
        int color = (int) Long.parseLong(hex.replace("#", ""), 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return Color.rgb(r, g, b);
    }

    /**
     * Returns the Android ICS holo blue light color.
     *
     * @return
     */
    public static int getHoloBlue() {
        return Color.rgb(51, 181, 229);
    }

    /**
     * Sets the alpha component of the given color.
     *
     * @param color
     * @param alpha 0 - 255
     * @return
     */
    public static int colorWithAlpha(int color, int alpha) {
        return (color & 0xffffff) | ((alpha & 0xff) << 24);
    }

    /**
     * turn an array of resource-colors (contains resource-id integers) into an
     * array list of actual color integers
     *
     * @param r
     * @param colors an integer array of resource id's of colors
     * @return
     */
    public static List<Integer> createColors(Resources r, int[] colors) {

        List<Integer> result = new ArrayList<Integer>();

        for (int i : colors) {
            result.add(r.getColor(i));
        }

        return result;
    }

    /**
     * Turns an array of colors (integer color values) into an ArrayList of
     * colors.
     *
     * @param colors
     * @return
     */
    public static List<Integer> createColors(int[] colors) {

        List<Integer> result = new ArrayList<Integer>();

        for (int i : colors) {
            result.add(i);
        }

        return result;
    }
}
