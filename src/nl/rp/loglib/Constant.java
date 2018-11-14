package nl.rp.loglib;

import java.util.ArrayList;

public enum Constant {

	START_FLAG(0b11111111),
	MAGIC_BYTE_V1_LITTLE_ENDIAN(0b01110000),
	MAGIC_BYTE_V1_BIG_ENDIAN(0b01110001),
	END_FLAG(0b11111110),

	//4 bytes: 10
	EVT_NULL(0),

	//6 bytes: 3
	EVT_CH8_BOOL8(8),
	EVT_CH8_INT8(9),
	EVT_CH8_UINT8(10),

	//7 bytes: 8
	EVT_CH8_INT16(11),
	EVT_CH8_UINT16(12),
	EVT_ID8_CH8_BOOL8(13),
	EVT_ID8_CH8_INT8(14),
	EVT_ID8_CH8_UINT8(15),
	EVT_CH16_BOOL8(16),
	EVT_CH16_INT8(17),
	EVT_CH16_UINT8(18),

	//8 bytes: 11
	EVT_TICK32(19),
	EVT_ID8_CH8_INT16(20),
	EVT_ID8_CH8_UINT16(21),
	EVT_GR8_ID8_CH8_BOOL8(22),
	EVT_GR8_ID8_CH8_INT8(23),
	EVT_GR8_ID8_CH8_UINT8(24),
	EVT_ID8_CH16_BOOL8(25),
	EVT_ID8_CH16_INT8(26),
	EVT_ID8_CH16_UINT8(27),
	EVT_CH16_INT16(28),
	EVT_CH16_UINT16(29),

	//9 bytes: 10
	EVT_CH8_INT32(30),
	EVT_CH8_UINT32(31),
	EVT_CH8_REAL32(32),
	EVT_GR8_ID8_CH8_INT16(33),
	EVT_GR8_ID8_CH8_UINT16(34),
	EVT_ID8_CH16_INT16(35),
	EVT_ID8_CH16_UINT16(36),
	EVT_GR8_ID8_CH16_BOOL8(37),
	EVT_GR8_ID8_CH16_INT8(38),
	EVT_GR8_ID8_CH16_UINT8(39),

	//10 bytes: 14
	EVT_ID8_CH8_INT32(40),
	EVT_ID8_CH8_UINT32(41),
	EVT_ID8_CH8_REAL32(42),
	EVT_GR8_ID16_CH16_BOOL8(43),
	EVT_GR8_ID16_CH16_INT8(44),
	EVT_GR8_ID16_CH16_UINT8(45),
	EVT_GR8_ID8_CH16_INT16(46),
	EVT_GR8_ID8_CH16_UINT16(47),
	EVT_CH16_INT32(48),
	EVT_CH16_UINT32(49),
	EVT_CH16_REAL32(50),
	EVT_CH8_TICK32_BOOL8(51),
	EVT_CH8_TICK32_INT8(52),
	EVT_CH8_TICK32_UINT8(53),

	//11 bytes: 19
	EVT_GR8_ID8_CH8_INT32(54),
	EVT_GR8_ID8_CH8_UINT32(55),
	EVT_GR8_ID8_CH8_REAL32(56),
	EVT_ID8_CH16_INT32(57),
	EVT_ID8_CH16_UINT32(58),
	EVT_ID8_CH16_REAL32(59),
	EVT_GR8_ID16_CH16_INT16(60),
	EVT_GR8_ID16_CH16_UINT16(61),
	EVT_GR16_ID16_CH16_BOOL8(62),
	EVT_GR16_ID16_CH16_INT8(63),
	EVT_GR16_ID16_CH16_UINT8(64),
	EVT_CH8_TICK32_INT16(65),
	EVT_CH8_TICK32_UINT16(66),
	EVT_ID8_CH8_TICK32_BOOL8(67),
	EVT_ID8_CH8_TICK32_INT8(68),
	EVT_ID8_CH8_TICK32_UINT8(69),
	EVT_CH16_TICK32_BOOL8(70),
	EVT_CH16_TICK32_INT8(71),
	EVT_CH16_TICK32_UINT8(72),

	//12 bytes: 16
	EVT_TICK64(73),
	EVT_GR16_ID16_CH16_INT16(74),
	EVT_GR16_ID16_CH16_UINT16(75),
	EVT_GR8_ID8_CH16_INT32(76),
	EVT_GR8_ID8_CH16_UINT32(77),
	EVT_GR8_ID8_CH16_REAL32(78),
	EVT_ID8_CH8_TICK32_INT16(79),
	EVT_ID8_CH8_TICK32_UINT16(80),
	EVT_GR8_ID8_CH8_TICK32_BOOL8(81),
	EVT_GR8_ID8_CH8_TICK32_INT8(82),
	EVT_GR8_ID8_CH8_TICK32_UINT8(83),
	EVT_ID8_CH16_TICK32_BOOL8(84),
	EVT_ID8_CH16_TICK32_INT8(85),
	EVT_ID8_CH16_TICK32_UINT8(86),
	EVT_CH16_TICK32_INT16(87),
	EVT_CH16_TICK32_UINT16(88),

	//13 bytes: 17
	//EVT_CH8_INT64(0),
	//EVT_CH8_UINT64(0),
	EVT_CH8_REAL64(89),
	EVT_GR8_ID16_CH16_INT32(90),
	EVT_GR8_ID16_CH16_UINT32(91),
	EVT_GR8_ID16_CH16_REAL32(92),
	EVT_CH8_TICK32_INT32(93),
	EVT_CH8_TICK32_UINT32(94),
	EVT_CH8_TICK32_REAL32(95),
	EVT_GR8_ID8_CH8_TICK32_INT16(96),
	EVT_GR8_ID8_CH8_TICK32_UINT16(97),
	EVT_ID8_CH16_TICK32_INT16(98),
	EVT_ID8_CH16_TICK32_UINT16(99),
	EVT_GR8_ID8_CH16_TICK32_BOOL8(100),
	EVT_GR8_ID8_CH16_TICK32_INT8(101),
	EVT_GR8_ID8_CH16_TICK32_UINT8(102),
	EVT_ID16_CH16_TICK32_BOOL8(103),
	EVT_ID16_CH16_TICK32_INT8(104),
	EVT_ID16_CH16_TICK32_UINT8(105),

	//14 bytes: 21
	//EVT_ID8_CH8_INT64(0),
	//EVT_ID8_CH8_UINT64(0),
	EVT_ID8_CH8_REAL64(106),
	EVT_GR16_ID16_CH16_INT32(107),
	EVT_GR16_ID16_CH16_UINT32(108),
	EVT_GR16_ID16_CH16_REAL32(109),
	//EVT_CH16_INT64(0),
	//EVT_CH16_UINT64(0),
	EVT_CH16_REAL64(110),
	EVT_ID8_CH8_TICK32_INT32(111),
	EVT_ID8_CH8_TICK32_UINT32(112),
	EVT_ID8_CH8_TICK32_REAL32(113),
	EVT_GR8_ID16_CH16_TICK32_BOOL8(114),
	EVT_GR8_ID16_CH16_TICK32_INT8(115),
	EVT_GR8_ID16_CH16_TICK32_UINT8(116),
	EVT_CH8_TICK64_BOOL8(117),
	EVT_CH8_TICK64_INT8(118),
	EVT_CH8_TICK64_UINT8(119),
	EVT_GR8_ID8_CH16_TICK32_INT16(120),
	EVT_GR8_ID8_CH16_TICK32_UINT16(121),
	EVT_CH16_TICK32_INT32(122),
	EVT_CH16_TICK32_UINT32(123),
	EVT_CH16_TICK32_REAL32(124),
	EVT_ID16_CH16_TICK32_INT16(125),
	EVT_ID16_CH16_TICK32_UINT16(126),

	//15 bytes: 18
	//EVT_GR8_ID8_CH8_INT64(0),
	//EVT_GR8_ID8_CH8_UINT64(0),
	EVT_GR8_ID8_CH8_REAL64(127),
	//EVT_ID8_CH16_INT64(0),
	//EVT_ID8_CH16_UINT64(0),
	EVT_ID8_CH16_REAL64(128),
	EVT_GR8_ID8_CH8_TICK32_INT32(129),
	EVT_GR8_ID8_CH8_TICK32_UINT32(130),
	EVT_GR8_ID8_CH8_TICK32_REAL32(131),
	EVT_ID8_CH16_TICK32_INT32(132),
	EVT_ID8_CH16_TICK32_UINT32(133),
	EVT_ID8_CH16_TICK32_REAL32(134),
	EVT_GR8_ID16_CH16_TICK32_INT16(135),
	EVT_GR8_ID16_CH16_TICK32_UINT16(136),
	EVT_CH8_TICK64_INT16(137),
	EVT_CH8_TICK64_UINT16(138),
	EVT_ID8_CH8_TICK64_BOOL8(139),
	EVT_ID8_CH8_TICK64_INT8(140),
	EVT_ID8_CH8_TICK64_UINT8(141),
	EVT_GR16_ID16_CH16_TICK32_BOOL8(142),
	EVT_GR16_ID16_CH16_TICK32_INT8(143),
	EVT_GR16_ID16_CH16_TICK32_UINT8(144),

	//16 bytes: 17
	//EVT_ID16_CH16_INT64(0),
	//EVT_ID16_CH16_UINT64(0),
	EVT_GR8_ID8_CH16_REAL64(145),
	EVT_ID8_CH8_TICK64_INT16(146),
	EVT_ID8_CH8_TICK64_UINT16(147),
	EVT_GR8_ID8_CH8_TICK64_BOOL8(148),
	EVT_GR8_ID8_CH8_TICK64_INT8(149),
	EVT_GR8_ID8_CH8_TICK64_UINT8(150),
	EVT_ID8_CH16_TICK64_BOOL8(151),
	EVT_ID8_CH16_TICK64_INT8(152),
	EVT_ID8_CH16_TICK64_UINT8(153),
	EVT_GR8_ID8_CH16_TICK32_INT32(154),
	EVT_GR8_ID8_CH16_TICK32_UINT32(155),
	EVT_GR8_ID8_CH16_TICK32_REAL32(156),
	EVT_ID16_CH16_TICK32_INT32(157),
	EVT_ID16_CH16_TICK32_UINT32(158),
	EVT_ID16_CH16_TICK32_REAL32(159),
	EVT_GR16_ID16_CH16_TICK32_INT16(160),
	EVT_GR16_ID16_CH16_TICK32_UINT16(161),

	//17 bytes: 12
	//EVT_GR8_ID16_CH16_INT64(0),
	//EVT_GR8_ID16_CH16_UINT64(0),
	EVT_GR8_ID16_CH16_REAL64(162),
	//EVT_CH8_TICK32_INT64(0),
	//EVT_CH8_TICK32_UINT64(0),
	EVT_CH8_TICK32_REAL64(163),
	EVT_GR8_ID16_CH16_TICK32_INT32(164),
	EVT_GR8_ID16_CH16_TICK32_UINT32(165),
	EVT_GR8_ID16_CH16_TICK32_REAL32(166),
	EVT_CH8_TICK64_INT32(167),
	EVT_CH8_TICK64_UINT32(168),
	EVT_CH8_TICK64_REAL32(169),
	EVT_GR8_ID8_CH8_TICK64_INT16(170),
	EVT_GR8_ID8_CH8_TICK64_UINT16(171),
	EVT_ID8_CH16_TICK64_INT16(172),
	EVT_ID8_CH16_TICK64_UINT16(173),

	//18 bytes: 15
	EVT_GR16_ID16_CH16_TICK32_INT32(174),
	EVT_GR16_ID16_CH16_TICK32_UINT32(175),
	EVT_GR16_ID16_CH16_TICK32_REAL32(176),
	//EVT_GR16_ID16_CH16_INT64(0),
	//EVT_GR16_ID16_CH16_UINT64(0),
	EVT_GR16_ID16_CH16_REAL64(177),
	//EVT_ID8_CH8_TICK32_INT64(0),
	//EVT_ID8_CH8_TICK32_UINT64(0),
	EVT_ID8_CH8_TICK32_REAL64(178),
	EVT_ID8_CH8_TICK64_INT32(179),
	EVT_ID8_CH8_TICK64_UINT32(180),
	EVT_ID8_CH8_TICK64_REAL32(181),
	EVT_GR8_ID16_CH16_TICK64_BOOL8(182),
	EVT_GR8_ID16_CH16_TICK64_INT8(183),
	EVT_GR8_ID16_CH16_TICK64_UINT8(184),
	//EVT_CH16_TICK32_INT64(0),
	//EVT_CH16_TICK32_UINT64(0),
	EVT_CH16_TICK32_REAL64(185),
	EVT_CH16_TICK64_INT32(186),
	EVT_CH16_TICK64_UINT32(187),
	EVT_CH16_TICK64_REAL32(188),

	//19 bytes: 10
	//EVT_GR8_ID8_CH8_TICK32_INT64(0),
	//EVT_GR8_ID8_CH8_TICK32_UINT64(0),
	EVT_GR8_ID8_CH8_TICK32_REAL64(189),
	//EVT_ID8_CH16_TICK32_INT64(0),
	//EVT_ID8_CH16_TICK32_UINT64(0),
	EVT_ID8_CH16_TICK32_REAL64(190),
	EVT_GR8_ID8_CH8_TICK64_INT32(191),
	EVT_GR8_ID8_CH8_TICK64_UINT32(192),
	EVT_GR8_ID8_CH8_TICK64_REAL32(193),
	EVT_ID8_CH16_TICK64_INT32(194),
	EVT_ID8_CH16_TICK64_UINT32(195),
	EVT_ID8_CH16_TICK64_REAL32(196),
	EVT_GR8_ID16_CH16_TICK64_INT16(197),
	EVT_GR8_ID16_CH16_TICK64_UINT16(198),

	//20 bytes: 5
	//EVT_ID16_CH16_TICK32_INT64(0),
	//EVT_ID16_CH16_TICK32_UINT64(0),
	EVT_GR8_ID8_CH16_TICK32_REAL64(199),
	EVT_ID16_CH16_TICK32_REAL64(200),
	EVT_ID16_CH16_TICK64_INT32(201),
	EVT_ID16_CH16_TICK64_UINT32(202),
	EVT_ID16_CH16_TICK64_REAL32(203),

	//21 bytes: 5
	//EVT_GR8_ID16_CH16_TICK32_INT64(0),
	//EVT_GR8_ID16_CH16_TICK32_UINT64(0),
	EVT_GR8_ID16_CH16_TICK32_REAL64(204),
	//EVT_CH8_TICK64_INT64(0),
	//EVT_CH8_TICK64_UINT64(0),
	EVT_CH8_TICK64_REAL64(205),
	EVT_GR8_ID16_CH16_TICK64_INT32(206),
	EVT_GR8_ID16_CH16_TICK64_UINT32(207),
	EVT_GR8_ID16_CH16_TICK64_REAL32(208),

	//22 bytes: 8
	//EVT_ID8_CH8_TICK64_INT64(0),
	//EVT_ID8_CH8_TICK64_UINT64(0),
	EVT_ID8_CH8_TICK64_REAL64(209),
	EVT_GR16_ID16_CH16_TICK32_INT64(210),
	EVT_GR16_ID16_CH16_TICK32_UINT64(211),
	EVT_GR16_ID16_CH16_TICK32_REAL64(212),
	EVT_GR16_ID16_CH16_TICK64_INT32(213),
	EVT_GR16_ID16_CH16_TICK64_UINT32(214),
	EVT_GR16_ID16_CH16_TICK64_REAL32(215),
	//EVT_CH16_TICK64_INT64(0),
	//EVT_CH16_TICK64_UINT64(0),
	EVT_CH16_TICK64_REAL64(216),

	//23 bytes: 4
	//EVT_GR8_ID8_CH8_TICK64_INT64(0),
	//EVT_GR8_ID8_CH8_TICK64_UINT64(0),
	EVT_GR8_ID8_CH8_TICK64_REAL64(217),
	EVT_ID8_CH16_TICK64_INT64(218),
	EVT_ID8_CH16_TICK64_UINT64(219),
	EVT_ID8_CH16_TICK64_REAL64(220),

	//24 bytes: 1
	//EVT_ID16_CH16_TICK64_INT64(0),
	//EVT_ID16_CH16_TICK64_UINT64(0),
	EVT_ID16_CH16_TICK64_REAL64(221),

	//25 bytes: 1
	//EVT_GR8_ID16_CH16_TICK64_INT64(0),
	//EVT_GR8_ID16_CH16_TICK64_UINT64(0),
	EVT_GR8_ID16_CH16_TICK64_REAL64(222),

	//26 bytes: 3
	EVT_GR16_ID16_CH16_TICK64_INT64(223),
	EVT_GR16_ID16_CH16_TICK64_UINT64(224),
	EVT_GR16_ID16_CH16_TICK64_REAL64(225),

	//X bytes: 29
	EVT_CH8_STRING(226),
	EVT_CH8_BYTES(227),
	EVT_CH16_STRING(228),
	EVT_CH16_BYTES(229),
	//EVT_ID8_CH8_STRING(0),
	//EVT_ID8_CH8_BYTES(0),
	//EVT_ID8_CH16_STRING(0),
	//EVT_ID8_CH16_BYTES(0),
	//EVT_ID16_CH16_STRING(0),
	//EVT_ID16_CH16_BYTES(0),
	EVT_GR8_ID8_CH8_STRING(230),
	EVT_GR8_ID8_CH8_BYTES(231),
	//EVT_GR8_ID8_CH16_STRING(0),
	//EVT_GR8_ID8_CH16_BYTES(0),
	//EVT_GR8_ID16_CH16_STRING(0),
	//EVT_GR8_ID16_CH16_BYTES(0),
	EVT_GR16_ID16_CH16_STRING(232),
	EVT_GR16_ID16_CH16_BYTES(233),
	EVT_CH8_TICK32_STRING(234),
	EVT_CH8_TICK32_BYTES(235),
	EVT_CH16_TICK32_STRING(236),
	EVT_CH16_TICK32_BYTES(237),
	//EVT_ID8_CH8_TICK32_STRING(0),
	//EVT_ID8_CH8_TICK32_BYTES(0),
	//EVT_ID8_CH16_TICK32_STRING(0),
	//EVT_ID8_CH16_TICK32_BYTES(0),
	//EVT_ID16_CH16_TICK32_STRING(0),
	//EVT_ID16_CH16_TICK32_BYTES(0),
	EVT_GR8_ID8_CH8_TICK32_STRING(238),
	EVT_GR8_ID8_CH8_TICK32_BYTES(239),
	EVT_GR8_ID8_CH16_TICK32_STRING(240),
	EVT_GR8_ID8_CH16_TICK32_BYTES(241),
	//EVT_GR8_ID16_CH16_TICK32_STRING(0),
	//EVT_GR8_ID16_CH16_TICK32_BYTES(0),
	EVT_GR16_ID16_CH16_TICK32_STRING(242),
	EVT_GR16_ID16_CH16_TICK32_BYTES(243),
	EVT_CH8_TICK64_STRING(244),
	EVT_CH8_TICK64_BYTES(245),
	EVT_CH16_TICK64_STRING(246),
	EVT_CH16_TICK64_BYTES(247),
	//EVT_ID8_CH8_TICK64_STRING(0),
	//EVT_ID8_CH8_TICK64_BYTES(0),
	//EVT_ID8_CH16_TICK64_STRING(0),
	//EVT_ID8_CH16_TICK64_BYTES(0),
	//EVT_ID16_CH16_TICK64_STRING(0),
	//EVT_ID16_CH16_TICK64_BYTES(0),
	EVT_GR8_ID8_CH8_TICK64_STRING(248),
	EVT_GR8_ID8_CH8_TICK64_BYTES(249),
	EVT_GR8_ID8_CH16_TICK64_STRING(250),
	EVT_GR8_ID8_CH16_TICK64_BYTES(251),
	//EVT_GR8_ID16_CH16_TICK64_STRING(0),
	//EVT_GR8_ID16_CH16_TICK64_BYTES(0),
	EVT_GR16_ID16_CH16_TICK64_STRING(252),
	EVT_GR16_ID16_CH16_TICK64_BYTES(253);

	public static final ArrayList<Constant> CORE_EVENTS_DEFAULT = new ArrayList<Constant>();
	static {

		CORE_EVENTS_DEFAULT.add(EVT_NULL);

		CORE_EVENTS_DEFAULT.add(EVT_TICK32);
		CORE_EVENTS_DEFAULT.add(EVT_TICK64);

		CORE_EVENTS_DEFAULT.add(EVT_CH8_TICK32_BOOL8);
		CORE_EVENTS_DEFAULT.add(EVT_CH8_TICK32_INT16);
		CORE_EVENTS_DEFAULT.add(EVT_CH8_TICK32_INT32);
		CORE_EVENTS_DEFAULT.add(EVT_CH8_TICK32_UINT32);
		CORE_EVENTS_DEFAULT.add(EVT_CH8_TICK32_REAL32);
		CORE_EVENTS_DEFAULT.add(EVT_CH8_TICK32_REAL64);
		CORE_EVENTS_DEFAULT.add(EVT_CH8_TICK32_STRING);

		CORE_EVENTS_DEFAULT.add(EVT_CH16_TICK32_BOOL8);
		CORE_EVENTS_DEFAULT.add(EVT_CH16_TICK32_INT16);
		CORE_EVENTS_DEFAULT.add(EVT_CH16_TICK32_INT32);
		CORE_EVENTS_DEFAULT.add(EVT_CH16_TICK32_UINT32);
		CORE_EVENTS_DEFAULT.add(EVT_CH16_TICK32_REAL32);
		CORE_EVENTS_DEFAULT.add(EVT_CH16_TICK32_REAL64);
		CORE_EVENTS_DEFAULT.add(EVT_CH16_TICK32_STRING);

		CORE_EVENTS_DEFAULT.add(EVT_GR8_ID8_CH8_TICK32_BOOL8);
		CORE_EVENTS_DEFAULT.add(EVT_GR8_ID8_CH8_TICK32_INT16);
		CORE_EVENTS_DEFAULT.add(EVT_GR8_ID8_CH8_TICK32_INT32);
		CORE_EVENTS_DEFAULT.add(EVT_GR8_ID8_CH8_TICK32_UINT32);
		CORE_EVENTS_DEFAULT.add(EVT_GR8_ID8_CH8_TICK32_REAL32);
		CORE_EVENTS_DEFAULT.add(EVT_GR8_ID8_CH8_TICK32_REAL64);
		CORE_EVENTS_DEFAULT.add(EVT_GR8_ID8_CH8_TICK32_STRING);

		CORE_EVENTS_DEFAULT.add(EVT_GR8_ID8_CH16_TICK32_BOOL8);
		CORE_EVENTS_DEFAULT.add(EVT_GR8_ID8_CH16_TICK32_INT16);
		CORE_EVENTS_DEFAULT.add(EVT_GR8_ID8_CH16_TICK32_INT32);
		CORE_EVENTS_DEFAULT.add(EVT_GR8_ID8_CH16_TICK32_UINT32);
		CORE_EVENTS_DEFAULT.add(EVT_GR8_ID8_CH16_TICK32_REAL32);
		CORE_EVENTS_DEFAULT.add(EVT_GR8_ID8_CH16_TICK32_REAL64);
		CORE_EVENTS_DEFAULT.add(EVT_GR8_ID8_CH16_TICK32_STRING);

	}

	public static final ArrayList<Constant> CORE_EVENTS_MIN = new ArrayList<Constant>();
	static {

		CORE_EVENTS_MIN.add(EVT_NULL);

		CORE_EVENTS_MIN.add(EVT_CH16_TICK32_BOOL8);
		CORE_EVENTS_MIN.add(EVT_CH16_TICK32_INT32);
		CORE_EVENTS_MIN.add(EVT_CH16_TICK32_REAL32);
		CORE_EVENTS_MIN.add(EVT_CH16_TICK32_STRING);

		CORE_EVENTS_MIN.add(EVT_GR8_ID8_CH16_TICK32_BOOL8);
		CORE_EVENTS_MIN.add(EVT_GR8_ID8_CH16_TICK32_INT32);
		CORE_EVENTS_MIN.add(EVT_GR8_ID8_CH16_TICK32_REAL32);
		CORE_EVENTS_MIN.add(EVT_GR8_ID8_CH16_TICK32_STRING);

	}

	public static final ArrayList<Constant> CORE_EVENTS_EXT = new ArrayList<Constant>();
	static {

		CORE_EVENTS_EXT.add(EVT_CH8_TICK32_INT8);
		CORE_EVENTS_EXT.add(EVT_CH8_TICK32_UINT8);
		CORE_EVENTS_EXT.add(EVT_CH8_TICK32_UINT16);
		CORE_EVENTS_EXT.add(EVT_CH8_TICK32_BYTES);

		CORE_EVENTS_EXT.add(EVT_CH16_TICK32_INT8);
		CORE_EVENTS_EXT.add(EVT_CH16_TICK32_UINT8);
		CORE_EVENTS_EXT.add(EVT_CH16_TICK32_UINT16);
		CORE_EVENTS_EXT.add(EVT_CH16_TICK32_BYTES);

		CORE_EVENTS_EXT.add(EVT_GR8_ID8_CH8_TICK32_INT8);
		CORE_EVENTS_EXT.add(EVT_GR8_ID8_CH8_TICK32_UINT8);
		CORE_EVENTS_EXT.add(EVT_GR8_ID8_CH8_TICK32_UINT16);
		CORE_EVENTS_EXT.add(EVT_GR8_ID8_CH8_TICK32_BYTES);

		CORE_EVENTS_EXT.add(EVT_GR8_ID8_CH16_TICK32_INT8);
		CORE_EVENTS_EXT.add(EVT_GR8_ID8_CH16_TICK32_UINT8);
		CORE_EVENTS_EXT.add(EVT_GR8_ID8_CH16_TICK32_UINT16);
		CORE_EVENTS_EXT.add(EVT_GR8_ID8_CH16_TICK32_BYTES);

	}
	
	public static final ArrayList<Constant> CORE_EVENTS_EXT_NOTICK = new ArrayList<Constant>();
	static {

		CORE_EVENTS_EXT_NOTICK.add(EVT_CH8_BOOL8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH8_INT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH8_UINT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH8_INT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH8_UINT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH8_INT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH8_UINT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH8_REAL32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH8_REAL64);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH8_STRING);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH8_BYTES);

		CORE_EVENTS_EXT_NOTICK.add(EVT_CH16_BOOL8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH16_INT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH16_UINT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH16_INT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH16_UINT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH16_INT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH16_UINT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH16_REAL32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH16_REAL64);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH16_STRING);
		CORE_EVENTS_EXT_NOTICK.add(EVT_CH16_BYTES);

		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH8_BOOL8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH8_INT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH8_UINT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH8_INT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH8_UINT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH8_INT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH8_UINT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH8_REAL32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH8_REAL64);
		//CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH8_STRING);
		//CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH8_BYTES);

		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH16_BOOL8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH16_INT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH16_UINT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH16_INT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH16_UINT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH16_INT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH16_UINT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH16_REAL32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH16_REAL64);
		//CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH16_STRING);
		//CORE_EVENTS_EXT_NOTICK.add(EVT_ID8_CH16_BYTES);

		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH8_BOOL8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH8_INT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH8_UINT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH8_INT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH8_UINT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH8_INT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH8_UINT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH8_REAL32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH8_REAL64);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH8_STRING);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH8_BYTES);

		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH16_BOOL8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH16_INT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH16_UINT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH16_INT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH16_UINT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH16_INT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH16_UINT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH16_REAL32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH16_REAL64);
		//CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH16_STRING);
		//CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID8_CH16_BYTES);

		//TODO: Remove?
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID16_CH16_BOOL8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID16_CH16_INT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID16_CH16_UINT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID16_CH16_INT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID16_CH16_UINT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID16_CH16_INT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID16_CH16_UINT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID16_CH16_REAL32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID16_CH16_REAL64);
		//CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID16_CH16_STRING);
		//CORE_EVENTS_EXT_NOTICK.add(EVT_GR8_ID16_CH16_BYTES);

		CORE_EVENTS_EXT_NOTICK.add(EVT_GR16_ID16_CH16_BOOL8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR16_ID16_CH16_INT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR16_ID16_CH16_UINT8);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR16_ID16_CH16_INT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR16_ID16_CH16_UINT16);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR16_ID16_CH16_INT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR16_ID16_CH16_UINT32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR16_ID16_CH16_REAL32);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR16_ID16_CH16_REAL64);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR16_ID16_CH16_STRING);
		CORE_EVENTS_EXT_NOTICK.add(EVT_GR16_ID16_CH16_BYTES);

	}

	public static final ArrayList<Constant> CORE_EVENTS_EXT_TICK32 = new ArrayList<Constant>();
	static {

		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH8_TICK32_BOOL8);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH8_TICK32_INT8);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH8_TICK32_UINT8);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH8_TICK32_INT16);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH8_TICK32_UINT16);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH8_TICK32_INT32);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH8_TICK32_UINT32);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH8_TICK32_REAL32);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH8_TICK32_REAL64);
		//CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH8_TICK32_STRING);
		//CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH8_TICK32_BYTES);

		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH16_TICK32_BOOL8);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH16_TICK32_INT8);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH16_TICK32_UINT8);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH16_TICK32_INT16);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH16_TICK32_UINT16);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH16_TICK32_INT32);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH16_TICK32_UINT32);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH16_TICK32_REAL32);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH16_TICK32_REAL64);
		//CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH16_TICK32_STRING);
		//CORE_EVENTS_EXT_TICK32.add(EVT_ID8_CH16_TICK32_BYTES);

		CORE_EVENTS_EXT_TICK32.add(EVT_ID16_CH16_TICK32_BOOL8);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID16_CH16_TICK32_INT8);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID16_CH16_TICK32_UINT8);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID16_CH16_TICK32_INT16);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID16_CH16_TICK32_UINT16);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID16_CH16_TICK32_INT32);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID16_CH16_TICK32_UINT32);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID16_CH16_TICK32_REAL32);
		CORE_EVENTS_EXT_TICK32.add(EVT_ID16_CH16_TICK32_REAL64);
		//CORE_EVENTS_EXT_TICK32.add(EVT_ID16_CH16_TICK32_STRING);
		//CORE_EVENTS_EXT_TICK32.add(EVT_ID16_CH16_TICK32_BYTES);

		//TODO: Remove?
		CORE_EVENTS_EXT_TICK32.add(EVT_GR8_ID16_CH16_TICK32_BOOL8);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR8_ID16_CH16_TICK32_INT8);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR8_ID16_CH16_TICK32_UINT8);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR8_ID16_CH16_TICK32_INT16);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR8_ID16_CH16_TICK32_UINT16);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR8_ID16_CH16_TICK32_INT32);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR8_ID16_CH16_TICK32_UINT32);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR8_ID16_CH16_TICK32_REAL32);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR8_ID16_CH16_TICK32_REAL64);
		//CORE_EVENTS_EXT_TICK32.add(EVT_GR8_ID16_CH16_TICK32_STRING);
		//CORE_EVENTS_EXT_TICK32.add(EVT_GR8_ID16_CH16_TICK32_BYTES);

		CORE_EVENTS_EXT_TICK32.add(EVT_GR16_ID16_CH16_TICK32_BOOL8);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR16_ID16_CH16_TICK32_INT8);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR16_ID16_CH16_TICK32_UINT8);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR16_ID16_CH16_TICK32_INT16);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR16_ID16_CH16_TICK32_UINT16);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR16_ID16_CH16_TICK32_INT32);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR16_ID16_CH16_TICK32_UINT32);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR16_ID16_CH16_TICK32_REAL32);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR16_ID16_CH16_TICK32_REAL64);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR16_ID16_CH16_TICK32_STRING);
		CORE_EVENTS_EXT_TICK32.add(EVT_GR16_ID16_CH16_TICK32_BYTES);

	}

	private final int value;

	Constant(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public static Constant getConstantForName(String name) {
		
		if (name == null || name.length() == 0) {
			return null;
		}
		
		for (Constant constant : Constant.values()) {
			if (name.equals(constant.name())) {
				return constant;
			}
		}
		
		return null;
		
	}

}
