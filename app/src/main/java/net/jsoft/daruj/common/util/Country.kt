package net.jsoft.daruj.common.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import net.jsoft.daruj.R

enum class Country(
    val dialCode: String,
    @StringRes val resId: Int
) {
    AFGHANISTAN(
        dialCode = "+93",
        resId = R.string.tx_country_af
    ),
    ALAND_ISLANDS(
        dialCode = "+358",
        resId = R.string.tx_country_ax
    ),
    ALBANIA(
        dialCode = "+355",
        resId = R.string.tx_country_al
    ),
    ALGERIA(
        dialCode = "+213",
        resId = R.string.tx_country_dz
    ),
    AMERICAN_SAMOA(
        dialCode = "+1684",
        resId = R.string.tx_country_as
    ),
    ANDORRA(
        dialCode = "+376",
        resId = R.string.tx_country_ad
    ),
    ANGOLA(
        dialCode = "+244",
        resId = R.string.tx_country_ao
    ),
    ANGUILLA(
        dialCode = "+1264",
        resId = R.string.tx_country_ai
    ),
    ANTARCTICA(
        dialCode = "+672",
        resId = R.string.tx_country_aq
    ),
    ANTIGUA_AND_BARBUDA(
        dialCode = "+1268",
        resId = R.string.tx_country_ag
    ),
    ARGENTINA(
        dialCode = "+54",
        resId = R.string.tx_country_ar
    ),
    ARMENIA(
        dialCode = "+374",
        resId = R.string.tx_country_am
    ),
    ARUBA(
        dialCode = "+297",
        resId = R.string.tx_country_aw
    ),
    AUSTRALIA(
        dialCode = "+61",
        resId = R.string.tx_country_au
    ),
    AUSTRIA(
        dialCode = "+43",
        resId = R.string.tx_country_at
    ),
    AZERBAIJAN(
        dialCode = "+994",
        resId = R.string.tx_country_az
    ),
    BAHAMAS(
        dialCode = "+1242",
        resId = R.string.tx_country_bs
    ),
    BAHRAIN(
        dialCode = "+973",
        resId = R.string.tx_country_bh
    ),
    BANGLADESH(
        dialCode = "+880",
        resId = R.string.tx_country_bd
    ),
    BARBADOS(
        dialCode = "+1246",
        resId = R.string.tx_country_bb
    ),
    BELARUS(
        dialCode = "+375",
        resId = R.string.tx_country_by
    ),
    BELGIUM(
        dialCode = "+32",
        resId = R.string.tx_country_be
    ),
    BELIZE(
        dialCode = "+501",
        resId = R.string.tx_country_bz
    ),
    BENIN(
        dialCode = "+229",
        resId = R.string.tx_country_bj
    ),
    BERMUDA(
        dialCode = "+1441",
        resId = R.string.tx_country_bm
    ),
    BHUTAN(
        dialCode = "+975",
        resId = R.string.tx_country_bt
    ),
    BOLIVIA_PLURINATIONAL_STATE_OF(
        dialCode = "+591",
        resId = R.string.tx_country_bo
    ),
    BOSNIA_AND_HERZEGOVINA(
        dialCode = "+387",
        resId = R.string.tx_country_ba
    ),
    BOTSWANA(
        dialCode = "+267",
        resId = R.string.tx_country_bw
    ),
    BRAZIL(
        dialCode = "+55",
        resId = R.string.tx_country_br
    ),
    BRITISH_INDIAN_OCEAN_TERRITORY(
        dialCode = "+246",
        resId = R.string.tx_country_io
    ),
    BRUNEI_DARUSSALAM(
        dialCode = "+673",
        resId = R.string.tx_country_bn
    ),
    BULGARIA(
        dialCode = "+359",
        resId = R.string.tx_country_bg
    ),
    BURKINA_FASO(
        dialCode = "+226",
        resId = R.string.tx_country_bf
    ),
    BURUNDI(
        dialCode = "+257",
        resId = R.string.tx_country_bi
    ),
    CAMBODIA(
        dialCode = "+855",
        resId = R.string.tx_country_kh
    ),
    CAMEROON(
        dialCode = "+237",
        resId = R.string.tx_country_cm
    ),
    CANADA(
        dialCode = "+1",
        resId = R.string.tx_country_ca
    ),
    CAPE_VERDE(
        dialCode = "+238",
        resId = R.string.tx_country_cv
    ),
    CAYMAN_ISLANDS(
        dialCode = "+ 345",
        resId = R.string.tx_country_ky
    ),
    CENTRAL_AFRICAN_REPUBLIC(
        dialCode = "+236",
        resId = R.string.tx_country_cf
    ),
    CHAD(
        dialCode = "+235",
        resId = R.string.tx_country_td
    ),
    CHILE(
        dialCode = "+56",
        resId = R.string.tx_country_cl
    ),
    CHINA(
        dialCode = "+86",
        resId = R.string.tx_country_cn
    ),
    CHRISTMAS_ISLAND(
        dialCode = "+61",
        resId = R.string.tx_country_cx
    ),
    COCOS_KEELING_ISLANDS(
        dialCode = "+61",
        resId = R.string.tx_country_cc
    ),
    COLOMBIA(
        dialCode = "+57",
        resId = R.string.tx_country_co
    ),
    COMOROS(
        dialCode = "+269",
        resId = R.string.tx_country_km
    ),
    CONGO(
        dialCode = "+242",
        resId = R.string.tx_country_cg
    ),
    CONGO_THE_DEMOCRATIC_REPUBLIC_OF_THE_CONGO(
        dialCode = "+243",
        resId = R.string.tx_country_cd
    ),
    COOK_ISLANDS(
        dialCode = "+682",
        resId = R.string.tx_country_ck
    ),
    COSTA_RICA(
        dialCode = "+506",
        resId = R.string.tx_country_cr
    ),
    COTE_DIVOIRE(
        dialCode = "+225",
        resId = R.string.tx_country_ci
    ),
    CROATIA(
        dialCode = "+385",
        resId = R.string.tx_country_hr
    ),
    CUBA(
        dialCode = "+53",
        resId = R.string.tx_country_cu
    ),
    CYPRUS(
        dialCode = "+357",
        resId = R.string.tx_country_cy
    ),
    CZECH_REPUBLIC(
        dialCode = "+420",
        resId = R.string.tx_country_cz
    ),
    DENMARK(
        dialCode = "+45",
        resId = R.string.tx_country_dk
    ),
    DJIBOUTI(
        dialCode = "+253",
        resId = R.string.tx_country_dj
    ),
    DOMINICA(
        dialCode = "+1767",
        resId = R.string.tx_country_dm
    ),
    DOMINICAN_REPUBLIC(
        dialCode = "+1849",
        resId = R.string.tx_country_do
    ),
    ECUADOR(
        dialCode = "+593",
        resId = R.string.tx_country_ec
    ),
    EGYPT(
        dialCode = "+20",
        resId = R.string.tx_country_eg
    ),
    EL_SALVADOR(
        dialCode = "+503",
        resId = R.string.tx_country_sv
    ),
    EQUATORIAL_GUINEA(
        dialCode = "+240",
        resId = R.string.tx_country_gq
    ),
    ERITREA(
        dialCode = "+291",
        resId = R.string.tx_country_er
    ),
    ESTONIA(
        dialCode = "+372",
        resId = R.string.tx_country_ee
    ),
    ETHIOPIA(
        dialCode = "+251",
        resId = R.string.tx_country_et
    ),
    FALKLAND_ISLANDS_MALVINAS(
        dialCode = "+500",
        resId = R.string.tx_country_fk
    ),
    FAROE_ISLANDS(
        dialCode = "+298",
        resId = R.string.tx_country_fo
    ),
    FIJI(
        dialCode = "+679",
        resId = R.string.tx_country_fj
    ),
    FINLAND(
        dialCode = "+358",
        resId = R.string.tx_country_fi
    ),
    FRANCE(
        dialCode = "+33",
        resId = R.string.tx_country_fr
    ),
    FRENCH_GUIANA(
        dialCode = "+594",
        resId = R.string.tx_country_gf
    ),
    FRENCH_POLYNESIA(
        dialCode = "+689",
        resId = R.string.tx_country_pf
    ),
    GABON(
        dialCode = "+241",
        resId = R.string.tx_country_ga
    ),
    GAMBIA(
        dialCode = "+220",
        resId = R.string.tx_country_gm
    ),
    GEORGIA(
        dialCode = "+995",
        resId = R.string.tx_country_ge
    ),
    GERMANY(
        dialCode = "+49",
        resId = R.string.tx_country_de
    ),
    GHANA(
        dialCode = "+233",
        resId = R.string.tx_country_gh
    ),
    GIBRALTAR(
        dialCode = "+350",
        resId = R.string.tx_country_gi
    ),
    GREECE(
        dialCode = "+30",
        resId = R.string.tx_country_gr
    ),
    GREENLAND(
        dialCode = "+299",
        resId = R.string.tx_country_gl
    ),
    GRENADA(
        dialCode = "+1473",
        resId = R.string.tx_country_gd
    ),
    GUADELOUPE(
        dialCode = "+590",
        resId = R.string.tx_country_gp
    ),
    GUAM(
        dialCode = "+1671",
        resId = R.string.tx_country_gu
    ),
    GUATEMALA(
        dialCode = "+502",
        resId = R.string.tx_country_gt
    ),
    GUERNSEY(
        dialCode = "+44",
        resId = R.string.tx_country_gg
    ),
    GUINEA(
        dialCode = "+224",
        resId = R.string.tx_country_gn
    ),
    GUINEA_BISSAU(
        dialCode = "+245",
        resId = R.string.tx_country_gw
    ),
    GUYANA(
        dialCode = "+595",
        resId = R.string.tx_country_gy
    ),
    HAITI(
        dialCode = "+509",
        resId = R.string.tx_country_ht
    ),
    HOLY_SEE_VATICAN_CITY_STATE(
        dialCode = "+379",
        resId = R.string.tx_country_va
    ),
    HONDURAS(
        dialCode = "+504",
        resId = R.string.tx_country_hn
    ),
    HONG_KONG(
        dialCode = "+852",
        resId = R.string.tx_country_hk
    ),
    HUNGARY(
        dialCode = "+36",
        resId = R.string.tx_country_hu
    ),
    ICELAND(
        dialCode = "+354",
        resId = R.string.tx_country_is
    ),
    INDIA(
        dialCode = "+91",
        resId = R.string.tx_country_in
    ),
    INDONESIA(
        dialCode = "+62",
        resId = R.string.tx_country_id
    ),
    IRAN_ISLAMIC_REPUBLIC_OF_PERSIAN_GULF(
        dialCode = "+98",
        resId = R.string.tx_country_ir
    ),
    IRAQ(
        dialCode = "+964",
        resId = R.string.tx_country_iq
    ),
    IRELAND(
        dialCode = "+353",
        resId = R.string.tx_country_ie
    ),
    ISLE_OF_MAN(
        dialCode = "+44",
        resId = R.string.tx_country_im
    ),
    ISRAEL(
        dialCode = "+972",
        resId = R.string.tx_country_il
    ),
    ITALY(
        dialCode = "+39",
        resId = R.string.tx_country_it
    ),
    JAMAICA(
        dialCode = "+1876",
        resId = R.string.tx_country_jm
    ),
    JAPAN(
        dialCode = "+81",
        resId = R.string.tx_country_jp
    ),
    JERSEY(
        dialCode = "+44",
        resId = R.string.tx_country_je
    ),
    JORDAN(
        dialCode = "+962",
        resId = R.string.tx_country_jo
    ),
    KAZAKHSTAN(
        dialCode = "+77",
        resId = R.string.tx_country_kz
    ),
    KENYA(
        dialCode = "+254",
        resId = R.string.tx_country_ke
    ),
    KIRIBATI(
        dialCode = "+686",
        resId = R.string.tx_country_ki
    ),
    KOREA_DEMOCRATIC_PEOPLES_REPUBLIC_OF_KOREA(
        dialCode = "+850",
        resId = R.string.tx_country_kp
    ),
    KOREA_REPUBLIC_OF_SOUTH_KOREA(
        dialCode = "+82",
        resId = R.string.tx_country_kr
    ),
    KUWAIT(
        dialCode = "+965",
        resId = R.string.tx_country_kw
    ),
    KYRGYZSTAN(
        dialCode = "+996",
        resId = R.string.tx_country_kg
    ),
    LAOS(
        dialCode = "+856",
        resId = R.string.tx_country_la
    ),
    LATVIA(
        dialCode = "+371",
        resId = R.string.tx_country_lv
    ),
    LEBANON(
        dialCode = "+961",
        resId = R.string.tx_country_lb
    ),
    LESOTHO(
        dialCode = "+266",
        resId = R.string.tx_country_ls
    ),
    LIBERIA(
        dialCode = "+231",
        resId = R.string.tx_country_lr
    ),
    LIBYAN_ARAB_JAMAHIRIYA(
        dialCode = "+218",
        resId = R.string.tx_country_ly
    ),
    LIECHTENSTEIN(
        dialCode = "+423",
        resId = R.string.tx_country_li
    ),
    LITHUANIA(
        dialCode = "+370",
        resId = R.string.tx_country_lt
    ),
    LUXEMBOURG(
        dialCode = "+352",
        resId = R.string.tx_country_lu
    ),
    MACAO(
        dialCode = "+853",
        resId = R.string.tx_country_mo
    ),
    MACEDONIA(
        dialCode = "+389",
        resId = R.string.tx_country_mk
    ),
    MADAGASCAR(
        dialCode = "+261",
        resId = R.string.tx_country_mg
    ),
    MALAWI(
        dialCode = "+265",
        resId = R.string.tx_country_mw
    ),
    MALAYSIA(
        dialCode = "+60",
        resId = R.string.tx_country_my
    ),
    MALDIVES(
        dialCode = "+960",
        resId = R.string.tx_country_mv
    ),
    MALI(
        dialCode = "+223",
        resId = R.string.tx_country_ml
    ),
    MALTA(
        dialCode = "+356",
        resId = R.string.tx_country_mt
    ),
    MARSHALL_ISLANDS(
        dialCode = "+692",
        resId = R.string.tx_country_mh
    ),
    MARTINIQUE(
        dialCode = "+596",
        resId = R.string.tx_country_mq
    ),
    MAURITANIA(
        dialCode = "+222",
        resId = R.string.tx_country_mr
    ),
    MAURITIUS(
        dialCode = "+230",
        resId = R.string.tx_country_mu
    ),
    MAYOTTE(
        dialCode = "+262",
        resId = R.string.tx_country_yt
    ),
    MEXICO(
        dialCode = "+52",
        resId = R.string.tx_country_mx
    ),
    MICRONESIA_FEDERATED_STATES_OF_MICRONESIA(
        dialCode = "+691",
        resId = R.string.tx_country_fm
    ),
    MOLDOVA(
        dialCode = "+373",
        resId = R.string.tx_country_md
    ),
    MONACO(
        dialCode = "+377",
        resId = R.string.tx_country_mc
    ),
    MONGOLIA(
        dialCode = "+976",
        resId = R.string.tx_country_mn
    ),
    MONTENEGRO(
        dialCode = "+382",
        resId = R.string.tx_country_me
    ),
    MONTSERRAT(
        dialCode = "+1664",
        resId = R.string.tx_country_ms
    ),
    MOROCCO(
        dialCode = "+212",
        resId = R.string.tx_country_ma
    ),
    MOZAMBIQUE(
        dialCode = "+258",
        resId = R.string.tx_country_mz
    ),
    MYANMAR(
        dialCode = "+95",
        resId = R.string.tx_country_mm
    ),
    NAMIBIA(
        dialCode = "+264",
        resId = R.string.tx_country_na
    ),
    NAURU(
        dialCode = "+674",
        resId = R.string.tx_country_nr
    ),
    NEPAL(
        dialCode = "+977",
        resId = R.string.tx_country_np
    ),
    NETHERLANDS(
        dialCode = "+31",
        resId = R.string.tx_country_nl
    ),
    NETHERLANDS_ANTILLES(
        dialCode = "+599",
        resId = R.string.tx_country_an
    ),
    NEW_CALEDONIA(
        dialCode = "+687",
        resId = R.string.tx_country_nc
    ),
    NEW_ZEALAND(
        dialCode = "+64",
        resId = R.string.tx_country_nz
    ),
    NICARAGUA(
        dialCode = "+505",
        resId = R.string.tx_country_ni
    ),
    NIGER(
        dialCode = "+227",
        resId = R.string.tx_country_ne
    ),
    NIGERIA(
        dialCode = "+234",
        resId = R.string.tx_country_ng
    ),
    NIUE(
        dialCode = "+683",
        resId = R.string.tx_country_nu
    ),
    NORFOLK_ISLAND(
        dialCode = "+672",
        resId = R.string.tx_country_nf
    ),
    NORTHERN_MARIANA_ISLANDS(
        dialCode = "+1670",
        resId = R.string.tx_country_mp
    ),
    NORWAY(
        dialCode = "+47",
        resId = R.string.tx_country_no
    ),
    OMAN(
        dialCode = "+968",
        resId = R.string.tx_country_om
    ),
    PAKISTAN(
        dialCode = "+92",
        resId = R.string.tx_country_pk
    ),
    PALAU(
        dialCode = "+680",
        resId = R.string.tx_country_pw
    ),
    PALESTINIAN_TERRITORY_OCCUPIED(
        dialCode = "+970",
        resId = R.string.tx_country_ps
    ),
    PANAMA(
        dialCode = "+507",
        resId = R.string.tx_country_pa
    ),
    PAPUA_NEW_GUINEA(
        dialCode = "+675",
        resId = R.string.tx_country_pg
    ),
    PARAGUAY(
        dialCode = "+595",
        resId = R.string.tx_country_py
    ),
    PERU(
        dialCode = "+51",
        resId = R.string.tx_country_pe
    ),
    PHILIPPINES(
        dialCode = "+63",
        resId = R.string.tx_country_ph
    ),
    PITCAIRN(
        dialCode = "+872",
        resId = R.string.tx_country_pn
    ),
    POLAND(
        dialCode = "+48",
        resId = R.string.tx_country_pl
    ),
    PORTUGAL(
        dialCode = "+351",
        resId = R.string.tx_country_pt
    ),
    PUERTO_RICO(
        dialCode = "+1939",
        resId = R.string.tx_country_pr
    ),
    QATAR(
        dialCode = "+974",
        resId = R.string.tx_country_qa
    ),
    ROMANIA(
        dialCode = "+40",
        resId = R.string.tx_country_ro
    ),
    RUSSIA(
        dialCode = "+7",
        resId = R.string.tx_country_ru
    ),
    RWANDA(
        dialCode = "+250",
        resId = R.string.tx_country_rw
    ),
    REUNION(
        dialCode = "+262",
        resId = R.string.tx_country_re
    ),
    SAINT_BARTHELEMY(
        dialCode = "+590",
        resId = R.string.tx_country_bl
    ),
    SAINT_HELENA_ASCENSION_AND_TRISTAN_DA_CUNHA(
        dialCode = "+290",
        resId = R.string.tx_country_sh
    ),
    SAINT_KITTS_AND_NEVIS(
        dialCode = "+1869",
        resId = R.string.tx_country_kn
    ),
    SAINT_LUCIA(
        dialCode = "+1758",
        resId = R.string.tx_country_lc
    ),
    SAINT_MARTIN(
        dialCode = "+590",
        resId = R.string.tx_country_mf
    ),
    SAINT_PIERRE_AND_MIQUELON(
        dialCode = "+508",
        resId = R.string.tx_country_pm
    ),
    SAINT_VINCENT_AND_THE_GRENADINES(
        dialCode = "+1784",
        resId = R.string.tx_country_vc
    ),
    SAMOA(
        dialCode = "+685",
        resId = R.string.tx_country_ws
    ),
    SAN_MARINO(
        dialCode = "+378",
        resId = R.string.tx_country_sm
    ),
    SAO_TOME_AND_PRINCIPE(
        dialCode = "+239",
        resId = R.string.tx_country_st
    ),
    SAUDI_ARABIA(
        dialCode = "+966",
        resId = R.string.tx_country_sa
    ),
    SENEGAL(
        dialCode = "+221",
        resId = R.string.tx_country_sn
    ),
    SERBIA(
        dialCode = "+381",
        resId = R.string.tx_country_rs
    ),
    SEYCHELLES(
        dialCode = "+248",
        resId = R.string.tx_country_sc
    ),
    SIERRA_LEONE(
        dialCode = "+232",
        resId = R.string.tx_country_sl
    ),
    SINGAPORE(
        dialCode = "+65",
        resId = R.string.tx_country_sg
    ),
    SLOVAKIA(
        dialCode = "+421",
        resId = R.string.tx_country_sk
    ),
    SLOVENIA(
        dialCode = "+386",
        resId = R.string.tx_country_si
    ),
    SOLOMON_ISLANDS(
        dialCode = "+677",
        resId = R.string.tx_country_sb
    ),
    SOMALIA(
        dialCode = "+252",
        resId = R.string.tx_country_so
    ),
    SOUTH_AFRICA(
        dialCode = "+27",
        resId = R.string.tx_country_za
    ),
    SOUTH_SUDAN(
        dialCode = "+211",
        resId = R.string.tx_country_ss
    ),
    SOUTH_GEORGIA_AND_THE_SOUTH_SANDWICH_ISLANDS(
        dialCode = "+500",
        resId = R.string.tx_country_gs
    ),
    SPAIN(
        dialCode = "+34",
        resId = R.string.tx_country_es
    ),
    SRI_LANKA(
        dialCode = "+94",
        resId = R.string.tx_country_lk
    ),
    SUDAN(
        dialCode = "+249",
        resId = R.string.tx_country_sd
    ),
    SURINAME(
        dialCode = "+597",
        resId = R.string.tx_country_sr
    ),
    SVALBARD_AND_JAN_MAYEN(
        dialCode = "+47",
        resId = R.string.tx_country_sj
    ),
    SWAZILAND(
        dialCode = "+268",
        resId = R.string.tx_country_sz
    ),
    SWEDEN(
        dialCode = "+46",
        resId = R.string.tx_country_se
    ),
    SWITZERLAND(
        dialCode = "+41",
        resId = R.string.tx_country_ch
    ),
    SYRIAN_ARAB_REPUBLIC(
        dialCode = "+963",
        resId = R.string.tx_country_sy
    ),
    TAIWAN(
        dialCode = "+886",
        resId = R.string.tx_country_tw
    ),
    TAJIKISTAN(
        dialCode = "+992",
        resId = R.string.tx_country_tj
    ),
    TANZANIA_UNITED_REPUBLIC_OF_TANZANIA(
        dialCode = "+255",
        resId = R.string.tx_country_tz
    ),
    THAILAND(
        dialCode = "+66",
        resId = R.string.tx_country_th
    ),
    TIMOR_LESTE(
        dialCode = "+670",
        resId = R.string.tx_country_tl
    ),
    TOGO(
        dialCode = "+228",
        resId = R.string.tx_country_tg
    ),
    TOKELAU(
        dialCode = "+690",
        resId = R.string.tx_country_tk
    ),
    TONGA(
        dialCode = "+676",
        resId = R.string.tx_country_to
    ),
    TRINIDAD_AND_TOBAGO(
        dialCode = "+1868",
        resId = R.string.tx_country_tt
    ),
    TUNISIA(
        dialCode = "+216",
        resId = R.string.tx_country_tn
    ),
    TURKEY(
        dialCode = "+90",
        resId = R.string.tx_country_tr
    ),
    TURKMENISTAN(
        dialCode = "+993",
        resId = R.string.tx_country_tm
    ),
    TURKS_AND_CAICOS_ISLANDS(
        dialCode = "+1649",
        resId = R.string.tx_country_tc
    ),
    TUVALU(
        dialCode = "+688",
        resId = R.string.tx_country_tv
    ),
    UGANDA(
        dialCode = "+256",
        resId = R.string.tx_country_ug
    ),
    UKRAINE(
        dialCode = "+380",
        resId = R.string.tx_country_ua
    ),
    UNITED_ARAB_EMIRATES(
        dialCode = "+971",
        resId = R.string.tx_country_ae
    ),
    UNITED_KINGDOM(
        dialCode = "+44",
        resId = R.string.tx_country_gb
    ),
    UNITED_STATES(
        dialCode = "+1",
        resId = R.string.tx_country_us
    ),
    URUGUAY(
        dialCode = "+598",
        resId = R.string.tx_country_uy
    ),
    UZBEKISTAN(
        dialCode = "+998",
        resId = R.string.tx_country_uz
    ),
    VANUATU(
        dialCode = "+678",
        resId = R.string.tx_country_vu
    ),
    VENEZUELA_BOLIVARIAN_REPUBLIC_OF_VENEZUELA(
        dialCode = "+58",
        resId = R.string.tx_country_ve
    ),
    VIETNAM(
        dialCode = "+84",
        resId = R.string.tx_country_vn
    ),
    VIRGIN_ISLANDS_BRITISH(
        dialCode = "+1284",
        resId = R.string.tx_country_vg
    ),
    VIRGIN_ISLANDS_US(
        dialCode = "+1340",
        resId = R.string.tx_country_vi
    ),
    WALLIS_AND_FUTUNA(
        dialCode = "+681",
        resId = R.string.tx_country_wf
    ),
    YEMEN(
        dialCode = "+967",
        resId = R.string.tx_country_ye
    ),
    ZAMBIA(
        dialCode = "+260",
        resId = R.string.tx_country_zm
    ),
    ZIMBABWE(
        dialCode = "+263",
        resId = R.string.tx_country_zw
    )
}