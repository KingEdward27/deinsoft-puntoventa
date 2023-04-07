-- phpMyAdmin SQL Dump
-- version 4.9.7
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 24-03-2023 a las 15:06:02
-- Versión del servidor: 5.7.41-cll-lve
-- Versión de PHP: 7.4.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `deinsoftcloud`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `act_caja`
--

CREATE TABLE `act_caja` (
  `act_caja_id` bigint(20) NOT NULL,
  `estado` varchar(1) DEFAULT NULL,
  `nombre` varchar(100) NOT NULL,
  `cnf_empresa_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `act_caja`
--

INSERT INTO `act_caja` (`act_caja_id`, `estado`, `nombre`, `cnf_empresa_id`) VALUES
(1, '1', 'CAJA 1', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `act_caja_operacion`
--

CREATE TABLE `act_caja_operacion` (
  `act_caja_operacion_id` bigint(20) NOT NULL,
  `estado` varchar(1) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `fecha_registro` datetime(6) DEFAULT NULL,
  `flag_ingreso` varchar(1) DEFAULT NULL,
  `monto` decimal(19,2) DEFAULT NULL,
  `act_caja_turno_id` bigint(20) DEFAULT NULL,
  `act_comprobante_id` bigint(20) DEFAULT NULL,
  `act_pago_id` bigint(20) DEFAULT NULL,
  `detail` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `act_caja_operacion`
--

INSERT INTO `act_caja_operacion` (`act_caja_operacion_id`, `estado`, `fecha`, `fecha_registro`, `flag_ingreso`, `monto`, `act_caja_turno_id`, `act_comprobante_id`, `act_pago_id`, `detail`) VALUES
(1, '1', '2023-02-22', '2023-02-22 02:55:48.008000', '2', '3500.00', 1, 1, NULL, NULL),
(2, '1', '2023-02-24', '2023-02-24 02:42:38.477000', '2', '800.00', 1, 4, NULL, NULL),
(3, '1', '2023-02-24', '2023-02-24 02:43:18.301000', '1', '100.00', 1, 5, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `act_caja_turno`
--

CREATE TABLE `act_caja_turno` (
  `act_caja_turno_id` bigint(20) NOT NULL,
  `estado` varchar(1) DEFAULT NULL,
  `fecha_apertura` datetime(6) DEFAULT NULL,
  `fecha_cierre` datetime(6) DEFAULT NULL,
  `monto_apertura` decimal(19,2) DEFAULT NULL,
  `monto_cierre` decimal(19,2) DEFAULT NULL,
  `act_caja_id` bigint(20) DEFAULT NULL,
  `seg_usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `act_caja_turno`
--

INSERT INTO `act_caja_turno` (`act_caja_turno_id`, `estado`, `fecha_apertura`, `fecha_cierre`, `monto_apertura`, `monto_cierre`, `act_caja_id`, `seg_usuario_id`) VALUES
(1, '1', '2023-02-21 21:47:43.000000', NULL, '0.00', NULL, 1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `act_comprobante`
--

CREATE TABLE `act_comprobante` (
  `act_comprobante_id` bigint(20) NOT NULL,
  `billete` decimal(19,2) DEFAULT NULL,
  `codigoqr` varchar(300) DEFAULT NULL,
  `descuento` decimal(19,2) DEFAULT NULL,
  `envio_pse_flag` varchar(1) DEFAULT NULL,
  `envio_pse_mensaje` varchar(1000) DEFAULT NULL,
  `fecha` date NOT NULL,
  `fecha_registro` datetime(6) NOT NULL,
  `flag_estado` varchar(1) NOT NULL,
  `flag_isventa` varchar(1) NOT NULL,
  `igv` decimal(19,2) DEFAULT NULL,
  `num_ticket` varchar(300) DEFAULT NULL,
  `numero` varchar(8) NOT NULL,
  `observacion` varchar(100) DEFAULT NULL,
  `serie` varchar(4) NOT NULL,
  `subtotal` decimal(19,2) DEFAULT NULL,
  `total` decimal(19,2) NOT NULL,
  `vuelto` decimal(19,2) DEFAULT NULL,
  `xmlhash` varchar(300) DEFAULT NULL,
  `cnf_forma_pago_id` bigint(20) DEFAULT NULL,
  `cnf_local_id` bigint(20) NOT NULL,
  `cnf_maestro_id` bigint(20) NOT NULL,
  `cnf_moneda_id` bigint(20) NOT NULL,
  `cnf_tipo_comprobante_id` bigint(20) NOT NULL,
  `inv_almacen_id` bigint(20) NOT NULL,
  `seg_usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `act_comprobante`
--

INSERT INTO `act_comprobante` (`act_comprobante_id`, `billete`, `codigoqr`, `descuento`, `envio_pse_flag`, `envio_pse_mensaje`, `fecha`, `fecha_registro`, `flag_estado`, `flag_isventa`, `igv`, `num_ticket`, `numero`, `observacion`, `serie`, `subtotal`, `total`, `vuelto`, `xmlhash`, `cnf_forma_pago_id`, `cnf_local_id`, `cnf_maestro_id`, `cnf_moneda_id`, `cnf_tipo_comprobante_id`, `inv_almacen_id`, `seg_usuario_id`) VALUES
(1, NULL, '', '0.00', '', '', '2023-02-21', '2023-02-22 02:55:47.477000', '1', '2', '533.90', '', '1234', '', 'FF01', '2966.10', '3500.00', NULL, '', 1, 1, 1, 1, 2, 1, 3),
(4, NULL, '', '0.00', '', '', '2023-02-23', '2023-02-24 02:42:37.751000', '1', '2', '122.03', '', '83590', '', '0001', '677.97', '800.00', NULL, '', 1, 1, 2, 2, 4, 1, 3),
(5, NULL, '', '0.00', '', '', '2023-02-23', '2023-02-24 02:43:17.833000', '1', '1', '15.25', '', '1', '', '0001', '84.75', '100.00', NULL, '', 1, 1, 2, 1, 4, 1, 3),
(6, NULL, '', '0.00', '0', '003-Error en lectura de archivos: no protocol: ', '2023-03-02', '2023-03-02 14:33:14.610356', '1', '1', '53.39', '', '1', '', 'BB01', '296.61', '350.00', NULL, '', 2, 1, 1, 1, 1, 1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `act_comprobante_detalle`
--

CREATE TABLE `act_comprobante_detalle` (
  `act_comprobante_detalle_id` bigint(20) NOT NULL,
  `afectacion_igv` decimal(19,2) NOT NULL,
  `cantidad` decimal(19,2) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `descuento` decimal(19,2) DEFAULT NULL,
  `importe` decimal(19,2) DEFAULT NULL,
  `precio` decimal(19,2) NOT NULL,
  `act_comprobante_id` bigint(20) NOT NULL,
  `cnf_impuesto_condicion_id` bigint(20) NOT NULL,
  `cnf_producto_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `act_comprobante_detalle`
--

INSERT INTO `act_comprobante_detalle` (`act_comprobante_detalle_id`, `afectacion_igv`, `cantidad`, `descripcion`, `descuento`, `importe`, `precio`, `act_comprobante_id`, `cnf_impuesto_condicion_id`, `cnf_producto_id`) VALUES
(1, '533.90', '10.00', 'BATERIA ETNA BGH 1232', NULL, '3500.00', '350.00', 1, 1, 1),
(3, '122.03', '10.00', 'Pikachu VMax', NULL, '800.00', '80.00', 4, 1, 2),
(4, '15.25', '1.00', 'Pikachu VMax', NULL, '100.00', '100.00', 5, 1, 2),
(5, '53.39', '1.00', 'BATERIA ETNA BGH 1232', NULL, '350.00', '350.00', 6, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `act_contrato`
--

CREATE TABLE `act_contrato` (
  `act_contrato_id` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `fecha_registro` datetime(6) NOT NULL,
  `flag_estado` varchar(1) NOT NULL,
  `nro_poste` varchar(10) DEFAULT NULL,
  `numero` varchar(8) NOT NULL,
  `observacion` varchar(100) DEFAULT NULL,
  `serie` varchar(4) NOT NULL,
  `url_map` varchar(1000) DEFAULT NULL,
  `cnf_forma_pago_id` bigint(20) DEFAULT NULL,
  `cnf_local_id` bigint(20) NOT NULL,
  `cnf_maestro_id` bigint(20) NOT NULL,
  `cnf_plan_contrato_id` bigint(20) NOT NULL,
  `cnf_tipo_comprobante_id` bigint(20) NOT NULL,
  `seg_usuario_id` bigint(20) DEFAULT NULL,
  `direccion` varchar(1000) DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `vicinity` varchar(1000) DEFAULT NULL,
  `cnf_zona_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `act_pago`
--

CREATE TABLE `act_pago` (
  `act_pago_id` bigint(20) NOT NULL,
  `billete` decimal(19,2) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `fecha_registro` datetime(6) NOT NULL,
  `igv` decimal(19,2) DEFAULT NULL,
  `numero` varchar(8) NOT NULL,
  `serie` varchar(4) NOT NULL,
  `subtotal` decimal(19,2) DEFAULT NULL,
  `total` decimal(19,2) DEFAULT NULL,
  `vuelto` decimal(19,2) DEFAULT NULL,
  `act_pago_programacion_id` bigint(20) DEFAULT NULL,
  `seg_usuario_id` bigint(20) DEFAULT NULL,
  `cnf_tipo_comprobante_id` bigint(20) NOT NULL,
  `cnf_local_id` bigint(20) DEFAULT NULL,
  `xmlhash` varchar(300) DEFAULT NULL,
  `cnf_maestro_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `act_pago_detalle`
--

CREATE TABLE `act_pago_detalle` (
  `act_pago_detalle_id` bigint(20) NOT NULL,
  `monto` decimal(19,2) DEFAULT NULL,
  `act_pago_id` bigint(20) DEFAULT NULL,
  `act_pago_programacion_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `act_pago_programacion`
--

CREATE TABLE `act_pago_programacion` (
  `act_pago_programacion_id` bigint(20) NOT NULL,
  `fecha` date DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `monto` decimal(19,2) DEFAULT NULL,
  `monto_pendiente` decimal(19,2) DEFAULT NULL,
  `act_comprobante_id` bigint(20) DEFAULT NULL,
  `act_contrato_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_categoria`
--

CREATE TABLE `cnf_categoria` (
  `cnf_categoria_id` bigint(20) NOT NULL,
  `flag_estado` varchar(1) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `cnf_empresa_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_categoria`
--

INSERT INTO `cnf_categoria` (`cnf_categoria_id`, `flag_estado`, `nombre`, `cnf_empresa_id`) VALUES
(1, NULL, 'BATERIAS', 1),
(4, NULL, 'Pokemon', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_distrito`
--

CREATE TABLE `cnf_distrito` (
  `cnf_distrito_id` bigint(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `value` varchar(16) DEFAULT NULL,
  `cnf_provincia_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_distrito`
--

INSERT INTO `cnf_distrito` (`cnf_distrito_id`, `nombre`, `value`, `cnf_provincia_id`) VALUES
(1, 'CHACHAPOYAS', NULL, 1),
(2, 'ASUNCION', NULL, 1),
(3, 'BALSAS', NULL, 1),
(4, 'CHETO', NULL, 1),
(5, 'CHILIQUIN', NULL, 1),
(6, 'CHUQUIBAMBA', NULL, 1),
(7, 'GRANADA', NULL, 1),
(8, 'HUANCAS', NULL, 1),
(9, 'LA JALCA', NULL, 1),
(10, 'LEIMEBAMBA', NULL, 1),
(11, 'LEVANTO', NULL, 1),
(12, 'MAGDALENA', NULL, 1),
(13, 'MARISCAL CASTILLA', NULL, 1),
(14, 'MOLINOPAMPA', NULL, 1),
(15, 'MONTEVIDEO', NULL, 1),
(16, 'OLLEROS', NULL, 1),
(17, 'QUINJALCA', NULL, 1),
(18, 'SAN FRANCISCO DE DAGUAS', NULL, 1),
(19, 'SAN ISIDRO DE MAINO', NULL, 1),
(20, 'SOLOCO', NULL, 1),
(21, 'SONCHE', NULL, 1),
(22, 'LA PECA', NULL, 2),
(23, 'ARAMANGO', NULL, 2),
(24, 'COPALLIN', NULL, 2),
(25, 'EL PARCO', NULL, 2),
(26, 'IMAZA', NULL, 2),
(27, 'JUMBILLA', NULL, 3),
(28, 'CHISQUILLA', NULL, 3),
(29, 'CHURUJA', NULL, 3),
(30, 'COROSHA', NULL, 3),
(31, 'CUISPES', NULL, 3),
(32, 'FLORIDA', NULL, 3),
(33, 'JAZAN', NULL, 3),
(34, 'RECTA', NULL, 3),
(35, 'SAN CARLOS', NULL, 3),
(36, 'SHIPASBAMBA', NULL, 3),
(37, 'VALERA', NULL, 3),
(38, 'YAMBRASBAMBA', NULL, 3),
(39, 'NIEVA', NULL, 4),
(40, 'EL CENEPA', NULL, 4),
(41, 'RIO SANTIAGO', NULL, 4),
(42, 'LAMUD', NULL, 5),
(43, 'CAMPORREDONDO', NULL, 5),
(44, 'COCABAMBA', NULL, 5),
(45, 'COLCAMAR', NULL, 5),
(46, 'CONILA', NULL, 5),
(47, 'INGUILPATA', NULL, 5),
(48, 'LONGUITA', NULL, 5),
(49, 'LONYA CHICO', NULL, 5),
(50, 'LUYA', NULL, 5),
(51, 'LUYA VIEJO', NULL, 5),
(52, 'MARIA', NULL, 5),
(53, 'OCALLI', NULL, 5),
(54, 'OCUMAL', NULL, 5),
(55, 'PISUQUIA', NULL, 5),
(56, 'PROVIDENCIA', NULL, 5),
(57, 'SAN CRISTOBAL', NULL, 5),
(58, 'SAN FRANCISCO DEL YESO', NULL, 5),
(59, 'SAN JERONIMO', NULL, 5),
(60, 'SAN JUAN DE LOPECANCHA', NULL, 5),
(61, 'SANTA CATALINA', NULL, 5),
(62, 'SANTO TOMAS', NULL, 5),
(63, 'TINGO', NULL, 5),
(64, 'TRITA', NULL, 5),
(65, 'SAN NICOLAS', NULL, 6),
(66, 'CHIRIMOTO', NULL, 6),
(67, 'COCHAMAL', NULL, 6),
(68, 'HUAMBO', NULL, 6),
(69, 'LIMABAMBA', NULL, 6),
(70, 'LONGAR', NULL, 6),
(71, 'MARISCAL BENAVIDES', NULL, 6),
(72, 'MILPUC', NULL, 6),
(73, 'OMIA', NULL, 6),
(74, 'SANTA ROSA', NULL, 6),
(75, 'TOTORA', NULL, 6),
(76, 'VISTA ALEGRE', NULL, 6),
(77, 'BAGUA GRANDE', NULL, 7),
(78, 'CAJARURO', NULL, 7),
(79, 'CUMBA', NULL, 7),
(80, 'EL MILAGRO', NULL, 7),
(81, 'JAMALCA', NULL, 7),
(82, 'LONYA GRANDE', NULL, 7),
(83, 'YAMON', NULL, 7),
(84, 'HUARAZ', NULL, 8),
(85, 'COCHABAMBA', NULL, 8),
(86, 'COLCABAMBA', NULL, 8),
(87, 'HUANCHAY', NULL, 8),
(88, 'INDEPENDENCIA', NULL, 8),
(89, 'JANGAS', NULL, 8),
(90, 'LA LIBERTAD', NULL, 8),
(91, 'OLLEROS', NULL, 8),
(92, 'PAMPAS', NULL, 8),
(93, 'PARIACOTO', NULL, 8),
(94, 'PIRA', NULL, 8),
(95, 'TARICA', NULL, 8),
(96, 'AIJA', NULL, 9),
(97, 'CORIS', NULL, 9),
(98, 'HUACLLAN', NULL, 9),
(99, 'LA MERCED', NULL, 9),
(100, 'SUCCHA', NULL, 9),
(101, 'LLAMELLIN', NULL, 10),
(102, 'ACZO', NULL, 10),
(103, 'CHACCHO', NULL, 10),
(104, 'CHINGAS', NULL, 10),
(105, 'MIRGAS', NULL, 10),
(106, 'SAN JUAN DE RONTOY', NULL, 10),
(107, 'CHACAS', NULL, 11),
(108, 'ACOCHACA', NULL, 11),
(109, 'CHIQUIAN', NULL, 12),
(110, 'ABELARDO PARDO LEZAMETA', NULL, 12),
(111, 'ANTONIO RAYMONDI', NULL, 12),
(112, 'AQUIA', NULL, 12),
(113, 'CAJACAY', NULL, 12),
(114, 'CANIS', NULL, 12),
(115, 'COLQUIOC', NULL, 12),
(116, 'HUALLANCA', NULL, 12),
(117, 'HUASTA', NULL, 12),
(118, 'HUAYLLACAYAN', NULL, 12),
(119, 'LA PRIMAVERA', NULL, 12),
(120, 'MANGAS', NULL, 12),
(121, 'PACLLON', NULL, 12),
(122, 'SAN MIGUEL DE CORPANQUI', NULL, 12),
(123, 'TICLLOS', NULL, 12),
(124, 'CARHUAZ', NULL, 13),
(125, 'ACOPAMPA', NULL, 13),
(126, 'AMASHCA', NULL, 13),
(127, 'ANTA', NULL, 13),
(128, 'ATAQUERO', NULL, 13),
(129, 'MARCARA', NULL, 13),
(130, 'PARIAHUANCA', NULL, 13),
(131, 'SAN MIGUEL DE ACO', NULL, 13),
(132, 'SHILLA', NULL, 13),
(133, 'TINCO', NULL, 13),
(134, 'YUNGAR', NULL, 13),
(135, 'SAN LUIS', NULL, 14),
(136, 'SAN NICOLAS', NULL, 14),
(137, 'YAUYA', NULL, 14),
(138, 'CASMA', NULL, 15),
(139, 'BUENA VISTA ALTA', NULL, 15),
(140, 'COMANDANTE NOEL', NULL, 15),
(141, 'YAUTAN', NULL, 15),
(142, 'CORONGO', NULL, 16),
(143, 'ACO', NULL, 16),
(144, 'BAMBAS', NULL, 16),
(145, 'CUSCA', NULL, 16),
(146, 'LA PAMPA', NULL, 16),
(147, 'YANAC', NULL, 16),
(148, 'YUPAN', NULL, 16),
(149, 'HUARI', NULL, 17),
(150, 'ANRA', NULL, 17),
(151, 'CAJAY', NULL, 17),
(152, 'CHAVIN DE HUANTAR', NULL, 17),
(153, 'HUACACHI', NULL, 17),
(154, 'HUACCHIS', NULL, 17),
(155, 'HUACHIS', NULL, 17),
(156, 'HUANTAR', NULL, 17),
(157, 'MASIN', NULL, 17),
(158, 'PAUCAS', NULL, 17),
(159, 'PONTO', NULL, 17),
(160, 'RAHUAPAMPA', NULL, 17),
(161, 'RAPAYAN', NULL, 17),
(162, 'SAN MARCOS', NULL, 17),
(163, 'SAN PEDRO DE CHANA', NULL, 17),
(164, 'UCO', NULL, 17),
(165, 'HUARMEY', NULL, 18),
(166, 'COCHAPETI', NULL, 18),
(167, 'CULEBRAS', NULL, 18),
(168, 'HUAYAN', NULL, 18),
(169, 'MALVAS', NULL, 18),
(170, 'CARAZ', NULL, 26),
(171, 'HUALLANCA', NULL, 26),
(172, 'HUATA', NULL, 26),
(173, 'HUAYLAS', NULL, 26),
(174, 'MATO', NULL, 26),
(175, 'PAMPAROMAS', NULL, 26),
(176, 'PUEBLO LIBRE', NULL, 26),
(177, 'SANTA CRUZ', NULL, 26),
(178, 'SANTO TORIBIO', NULL, 26),
(179, 'YURACMARCA', NULL, 26),
(180, 'PISCOBAMBA', NULL, 27),
(181, 'CASCA', NULL, 27),
(182, 'ELEAZAR GUZMAN BARRON', NULL, 27),
(183, 'FIDEL OLIVAS ESCUDERO', NULL, 27),
(184, 'LLAMA', NULL, 27),
(185, 'LLUMPA', NULL, 27),
(186, 'LUCMA', NULL, 27),
(187, 'MUSGA', NULL, 27),
(188, 'OCROS', NULL, 21),
(189, 'ACAS', NULL, 21),
(190, 'CAJAMARQUILLA', NULL, 21),
(191, 'CARHUAPAMPA', NULL, 21),
(192, 'COCHAS', NULL, 21),
(193, 'CONGAS', NULL, 21),
(194, 'LLIPA', NULL, 21),
(195, 'SAN CRISTOBAL DE RAJAN', NULL, 21),
(196, 'SAN PEDRO', NULL, 21),
(197, 'SANTIAGO DE CHILCAS', NULL, 21),
(198, 'CABANA', NULL, 22),
(199, 'BOLOGNESI', NULL, 22),
(200, 'CONCHUCOS', NULL, 22),
(201, 'HUACASCHUQUE', NULL, 22),
(202, 'HUANDOVAL', NULL, 22),
(203, 'LACABAMBA', NULL, 22),
(204, 'LLAPO', NULL, 22),
(205, 'PALLASCA', NULL, 22),
(206, 'PAMPAS', NULL, 22),
(207, 'SANTA ROSA', NULL, 22),
(208, 'TAUCA', NULL, 22),
(209, 'POMABAMBA', NULL, 23),
(210, 'HUAYLLAN', NULL, 23),
(211, 'PAROBAMBA', NULL, 23),
(212, 'QUINUABAMBA', NULL, 23),
(213, 'RECUAY', NULL, 24),
(214, 'CATAC', NULL, 24),
(215, 'COTAPARACO', NULL, 24),
(216, 'HUAYLLAPAMPA', NULL, 24),
(217, 'LLACLLIN', NULL, 24),
(218, 'MARCA', NULL, 24),
(219, 'PAMPAS CHICO', NULL, 24),
(220, 'PARARIN', NULL, 24),
(221, 'TAPACOCHA', NULL, 24),
(222, 'TICAPAMPA', NULL, 24),
(223, 'CHIMBOTE', NULL, 25),
(224, 'CACERES DEL PERU', NULL, 25),
(225, 'COISHCO', NULL, 25),
(226, 'MACATE', NULL, 25),
(227, 'MORO', NULL, 25),
(228, 'NEPE&Ntilde;A', NULL, 25),
(229, 'SAMANCO', NULL, 25),
(230, 'SANTA', NULL, 25),
(231, 'NUEVO CHIMBOTE', NULL, 25),
(232, 'SIHUAS', NULL, 26),
(233, 'ACOBAMBA', NULL, 26),
(234, 'ALFONSO UGARTE', NULL, 26),
(235, 'CASHAPAMPA', NULL, 26),
(236, 'CHINGALPO', NULL, 26),
(237, 'HUAYLLABAMBA', NULL, 26),
(238, 'QUICHES', NULL, 26),
(239, 'RAGASH', NULL, 26),
(240, 'SAN JUAN', NULL, 26),
(241, 'SICSIBAMBA', NULL, 26),
(242, 'YUNGAY', NULL, 27),
(243, 'CASCAPARA', NULL, 27),
(244, 'MANCOS', NULL, 27),
(245, 'MATACOTO', NULL, 27),
(246, 'QUILLO', NULL, 27),
(247, 'RANRAHIRCA', NULL, 27),
(248, 'SHUPLUY', NULL, 27),
(249, 'YANAMA', NULL, 27),
(250, 'ABANCAY', NULL, 28),
(251, 'CHACOCHE', NULL, 28),
(252, 'CIRCA', NULL, 28),
(253, 'CURAHUASI', NULL, 28),
(254, 'HUANIPACA', NULL, 28),
(255, 'LAMBRAMA', NULL, 28),
(256, 'PICHIRHUA', NULL, 28),
(257, 'SAN PEDRO DE CACHORA', NULL, 28),
(258, 'TAMBURCO', NULL, 28),
(259, 'ANDAHUAYLAS', NULL, 29),
(260, 'ANDARAPA', NULL, 29),
(261, 'CHIARA', NULL, 29),
(262, 'HUANCARAMA', NULL, 29),
(263, 'HUANCARAY', NULL, 29),
(264, 'HUAYANA', NULL, 29),
(265, 'KISHUARA', NULL, 29),
(266, 'PACOBAMBA', NULL, 29),
(267, 'PACUCHA', NULL, 29),
(268, 'PAMPACHIRI', NULL, 29),
(269, 'POMACOCHA', NULL, 29),
(270, 'SAN ANTONIO DE CACHI', NULL, 29),
(271, 'SAN JERONIMO', NULL, 29),
(272, 'SAN MIGUEL DE CHACCRAMPA', NULL, 29),
(273, 'SANTA MARIA DE CHICMO', NULL, 29),
(274, 'TALAVERA', NULL, 29),
(275, 'TUMAY HUARACA', NULL, 29),
(276, 'TURPO', NULL, 29),
(277, 'KAQUIABAMBA', NULL, 29),
(278, 'ANTABAMBA', NULL, 30),
(279, 'EL ORO', NULL, 30),
(280, 'HUAQUIRCA', NULL, 30),
(281, 'JUAN ESPINOZA MEDRANO', NULL, 30),
(282, 'OROPESA', NULL, 30),
(283, 'PACHACONAS', NULL, 30),
(284, 'SABAINO', NULL, 30),
(285, 'CHALHUANCA', NULL, 31),
(286, 'CAPAYA', NULL, 31),
(287, 'CARAYBAMBA', NULL, 31),
(288, 'CHAPIMARCA', NULL, 31),
(289, 'COLCABAMBA', NULL, 31),
(290, 'COTARUSE', NULL, 31),
(291, 'HUAYLLO', NULL, 31),
(292, 'JUSTO APU SAHUARAURA', NULL, 31),
(293, 'LUCRE', NULL, 31),
(294, 'POCOHUANCA', NULL, 31),
(295, 'SAN JUAN DE CHAC&Ntilde;A', NULL, 31),
(296, 'SA&Ntilde;AYCA', NULL, 31),
(297, 'SORAYA', NULL, 31),
(298, 'TAPAIRIHUA', NULL, 31),
(299, 'TINTAY', NULL, 31),
(300, 'TORAYA', NULL, 31),
(301, 'YANACA', NULL, 31),
(302, 'TAMBOBAMBA', NULL, 32),
(303, 'COTABAMBAS', NULL, 32),
(304, 'COYLLURQUI', NULL, 32),
(305, 'HAQUIRA', NULL, 32),
(306, 'MARA', NULL, 32),
(307, 'CHALLHUAHUACHO', NULL, 32),
(308, 'CHINCHEROS', NULL, 33),
(309, 'ANCO-HUALLO', NULL, 33),
(310, 'COCHARCAS', NULL, 33),
(311, 'HUACCANA', NULL, 33),
(312, 'OCOBAMBA', NULL, 33),
(313, 'ONGOY', NULL, 33),
(314, 'URANMARCA', NULL, 33),
(315, 'RANRACANCHA', NULL, 33),
(316, 'CHUQUIBAMBILLA', NULL, 34),
(317, 'CURPAHUASI', NULL, 34),
(318, 'GAMARRA', NULL, 34),
(319, 'HUAYLLATI', NULL, 34),
(320, 'MAMARA', NULL, 34),
(321, 'MICAELA BASTIDAS', NULL, 34),
(322, 'PATAYPAMPA', NULL, 34),
(323, 'PROGRESO', NULL, 34),
(324, 'SAN ANTONIO', NULL, 34),
(325, 'SANTA ROSA', NULL, 34),
(326, 'TURPAY', NULL, 34),
(327, 'VILCABAMBA', NULL, 34),
(328, 'VIRUNDO', NULL, 34),
(329, 'CURASCO', NULL, 34),
(330, 'AREQUIPA', NULL, 35),
(331, 'ALTO SELVA ALEGRE', NULL, 35),
(332, 'CAYMA', NULL, 35),
(333, 'CERRO COLORADO', NULL, 35),
(334, 'CHARACATO', NULL, 35),
(335, 'CHIGUATA', NULL, 35),
(336, 'JACOBO HUNTER', NULL, 35),
(337, 'LA JOYA', NULL, 35),
(338, 'MARIANO MELGAR', NULL, 35),
(339, 'MIRAFLORES', NULL, 35),
(340, 'MOLLEBAYA', NULL, 35),
(341, 'PAUCARPATA', NULL, 35),
(342, 'POCSI', NULL, 35),
(343, 'POLOBAYA', NULL, 35),
(344, 'QUEQUE&Ntilde;A', NULL, 35),
(345, 'SABANDIA', NULL, 35),
(346, 'SACHACA', NULL, 35),
(347, 'SAN JUAN DE SIGUAS', NULL, 35),
(348, 'SAN JUAN DE TARUCANI', NULL, 35),
(349, 'SANTA ISABEL DE SIGUAS', NULL, 35),
(350, 'SANTA RITA DE SIGUAS', NULL, 35),
(351, 'SOCABAYA', NULL, 35),
(352, 'TIABAYA', NULL, 35),
(353, 'UCHUMAYO', NULL, 35),
(354, 'VITOR', NULL, 35),
(355, 'YANAHUARA', NULL, 35),
(356, 'YARABAMBA', NULL, 35),
(357, 'YURA', NULL, 35),
(358, 'JOSE LUIS BUSTAMANTE Y RIVERO', NULL, 35),
(359, 'CAMANA', NULL, 36),
(360, 'JOSE MARIA QUIMPER', NULL, 36),
(361, 'MARIANO NICOLAS VALCARCEL', NULL, 36),
(362, 'MARISCAL CACERES', NULL, 36),
(363, 'NICOLAS DE PIEROLA', NULL, 36),
(364, 'OCO&Ntilde;A', NULL, 36),
(365, 'QUILCA', NULL, 36),
(366, 'SAMUEL PASTOR', NULL, 36),
(367, 'CARAVELI', NULL, 37),
(368, 'ACARI', NULL, 37),
(369, 'ATICO', NULL, 37),
(370, 'ATIQUIPA', NULL, 37),
(371, 'BELLA UNION', NULL, 37),
(372, 'CAHUACHO', NULL, 37),
(373, 'CHALA', NULL, 37),
(374, 'CHAPARRA', NULL, 37),
(375, 'HUANUHUANU', NULL, 37),
(376, 'JAQUI', NULL, 37),
(377, 'LOMAS', NULL, 37),
(378, 'QUICACHA', NULL, 37),
(379, 'YAUCA', NULL, 37),
(380, 'APLAO', NULL, 38),
(381, 'ANDAGUA', NULL, 38),
(382, 'AYO', NULL, 38),
(383, 'CHACHAS', NULL, 38),
(384, 'CHILCAYMARCA', NULL, 38),
(385, 'CHOCO', NULL, 38),
(386, 'HUANCARQUI', NULL, 38),
(387, 'MACHAGUAY', NULL, 38),
(388, 'ORCOPAMPA', NULL, 38),
(389, 'PAMPACOLCA', NULL, 38),
(390, 'TIPAN', NULL, 38),
(391, 'U&Ntilde;ON', NULL, 38),
(392, 'URACA', NULL, 38),
(393, 'VIRACO', NULL, 38),
(394, 'CHIVAY', NULL, 39),
(395, 'ACHOMA', NULL, 39),
(396, 'CABANACONDE', NULL, 39),
(397, 'CALLALLI', NULL, 39),
(398, 'CAYLLOMA', NULL, 39),
(399, 'COPORAQUE', NULL, 39),
(400, 'HUAMBO', NULL, 39),
(401, 'HUANCA', NULL, 39),
(402, 'ICHUPAMPA', NULL, 39),
(403, 'LARI', NULL, 39),
(404, 'LLUTA', NULL, 39),
(405, 'MACA', NULL, 39),
(406, 'MADRIGAL', NULL, 39),
(407, 'SAN ANTONIO DE CHUCA', NULL, 39),
(408, 'SIBAYO', NULL, 39),
(409, 'TAPAY', NULL, 39),
(410, 'TISCO', NULL, 39),
(411, 'TUTI', NULL, 39),
(412, 'YANQUE', NULL, 39),
(413, 'MAJES', NULL, 39),
(414, 'CHUQUIBAMBA', NULL, 40),
(415, 'ANDARAY', NULL, 40),
(416, 'CAYARANI', NULL, 40),
(417, 'CHICHAS', NULL, 40),
(418, 'IRAY', NULL, 40),
(419, 'RIO GRANDE', NULL, 40),
(420, 'SALAMANCA', NULL, 40),
(421, 'YANAQUIHUA', NULL, 40),
(422, 'MOLLENDO', NULL, 41),
(423, 'COCACHACRA', NULL, 41),
(424, 'DEAN VALDIVIA', NULL, 41),
(425, 'ISLAY', NULL, 41),
(426, 'MEJIA', NULL, 41),
(427, 'PUNTA DE BOMBON', NULL, 41),
(428, 'COTAHUASI', NULL, 42),
(429, 'ALCA', NULL, 42),
(430, 'CHARCANA', NULL, 42),
(431, 'HUAYNACOTAS', NULL, 42),
(432, 'PAMPAMARCA', NULL, 42),
(433, 'PUYCA', NULL, 42),
(434, 'QUECHUALLA', NULL, 42),
(435, 'SAYLA', NULL, 42),
(436, 'TAURIA', NULL, 42),
(437, 'TOMEPAMPA', NULL, 42),
(438, 'TORO', NULL, 42),
(439, 'AYACUCHO', NULL, 43),
(440, 'ACOCRO', NULL, 43),
(441, 'ACOS VINCHOS', NULL, 43),
(442, 'CARMEN ALTO', NULL, 43),
(443, 'CHIARA', NULL, 43),
(444, 'OCROS', NULL, 43),
(445, 'PACAYCASA', NULL, 43),
(446, 'QUINUA', NULL, 43),
(447, 'SAN JOSE DE TICLLAS', NULL, 43),
(448, 'SAN JUAN BAUTISTA', NULL, 43),
(449, 'SANTIAGO DE PISCHA', NULL, 43),
(450, 'SOCOS', NULL, 43),
(451, 'TAMBILLO', NULL, 43),
(452, 'VINCHOS', NULL, 43),
(453, 'JESUS NAZARENO', NULL, 43),
(454, 'CANGALLO', NULL, 44),
(455, 'CHUSCHI', NULL, 44),
(456, 'LOS MOROCHUCOS', NULL, 44),
(457, 'MARIA PARADO DE BELLIDO', NULL, 44),
(458, 'PARAS', NULL, 44),
(459, 'TOTOS', NULL, 44),
(460, 'SANCOS', NULL, 45),
(461, 'CARAPO', NULL, 45),
(462, 'SACSAMARCA', NULL, 45),
(463, 'SANTIAGO DE LUCANAMARCA', NULL, 45),
(464, 'HUANTA', NULL, 46),
(465, 'AYAHUANCO', NULL, 46),
(466, 'HUAMANGUILLA', NULL, 46),
(467, 'IGUAIN', NULL, 46),
(468, 'LURICOCHA', NULL, 46),
(469, 'SANTILLANA', NULL, 46),
(470, 'SIVIA', NULL, 46),
(471, 'LLOCHEGUA', NULL, 46),
(472, 'SAN MIGUEL', NULL, 47),
(473, 'ANCO', NULL, 47),
(474, 'AYNA', NULL, 47),
(475, 'CHILCAS', NULL, 47),
(476, 'CHUNGUI', NULL, 47),
(477, 'LUIS CARRANZA', NULL, 47),
(478, 'SANTA ROSA', NULL, 47),
(479, 'TAMBO', NULL, 47),
(480, 'PUQUIO', NULL, 48),
(481, 'AUCARA', NULL, 48),
(482, 'CABANA', NULL, 48),
(483, 'CARMEN SALCEDO', NULL, 48),
(484, 'CHAVI&Ntilde;A', NULL, 48),
(485, 'CHIPAO', NULL, 48),
(486, 'HUAC-HUAS', NULL, 48),
(487, 'LARAMATE', NULL, 48),
(488, 'LEONCIO PRADO', NULL, 48),
(489, 'LLAUTA', NULL, 48),
(490, 'LUCANAS', NULL, 48),
(491, 'OCA&Ntilde;A', NULL, 48),
(492, 'OTOCA', NULL, 48),
(493, 'SAISA', NULL, 48),
(494, 'SAN CRISTOBAL', NULL, 48),
(495, 'SAN JUAN', NULL, 48),
(496, 'SAN PEDRO', NULL, 48),
(497, 'SAN PEDRO DE PALCO', NULL, 48),
(498, 'SANCOS', NULL, 48),
(499, 'SANTA ANA DE HUAYCAHUACHO', NULL, 48),
(500, 'SANTA LUCIA', NULL, 48),
(501, 'CORACORA', NULL, 49),
(502, 'CHUMPI', NULL, 49),
(503, 'CORONEL CASTA&Ntilde;EDA', NULL, 49),
(504, 'PACAPAUSA', NULL, 49),
(505, 'PULLO', NULL, 49),
(506, 'PUYUSCA', NULL, 49),
(507, 'SAN FRANCISCO DE RAVACAYCO', NULL, 49),
(508, 'UPAHUACHO', NULL, 49),
(509, 'PAUSA', NULL, 50),
(510, 'COLTA', NULL, 50),
(511, 'CORCULLA', NULL, 50),
(512, 'LAMPA', NULL, 50),
(513, 'MARCABAMBA', NULL, 50),
(514, 'OYOLO', NULL, 50),
(515, 'PARARCA', NULL, 50),
(516, 'SAN JAVIER DE ALPABAMBA', NULL, 50),
(517, 'SAN JOSE DE USHUA', NULL, 50),
(518, 'SARA SARA', NULL, 50),
(519, 'QUEROBAMBA', NULL, 51),
(520, 'BELEN', NULL, 51),
(521, 'CHALCOS', NULL, 51),
(522, 'CHILCAYOC', NULL, 51),
(523, 'HUACA&Ntilde;A', NULL, 51),
(524, 'MORCOLLA', NULL, 51),
(525, 'PAICO', NULL, 51),
(526, 'SAN PEDRO DE LARCAY', NULL, 51),
(527, 'SAN SALVADOR DE QUIJE', NULL, 51),
(528, 'SANTIAGO DE PAUCARAY', NULL, 51),
(529, 'SORAS', NULL, 51),
(530, 'HUANCAPI', NULL, 52),
(531, 'ALCAMENCA', NULL, 52),
(532, 'APONGO', NULL, 52),
(533, 'ASQUIPATA', NULL, 52),
(534, 'CANARIA', NULL, 52),
(535, 'CAYARA', NULL, 52),
(536, 'COLCA', NULL, 52),
(537, 'HUAMANQUIQUIA', NULL, 52),
(538, 'HUANCARAYLLA', NULL, 52),
(539, 'HUAYA', NULL, 52),
(540, 'SARHUA', NULL, 52),
(541, 'VILCANCHOS', NULL, 52),
(542, 'VILCAS HUAMAN', NULL, 53),
(543, 'ACCOMARCA', NULL, 53),
(544, 'CARHUANCA', NULL, 53),
(545, 'CONCEPCION', NULL, 53),
(546, 'HUAMBALPA', NULL, 53),
(547, 'INDEPENDENCIA', NULL, 53),
(548, 'SAURAMA', NULL, 53),
(549, 'VISCHONGO', NULL, 53),
(550, 'CAJAMARCA', NULL, 54),
(551, 'CAJAMARCA', NULL, 54),
(552, 'ASUNCION', NULL, 54),
(553, 'CHETILLA', NULL, 54),
(554, 'COSPAN', NULL, 54),
(555, 'ENCA&Ntilde;ADA', NULL, 54),
(556, 'JESUS', NULL, 54),
(557, 'LLACANORA', NULL, 54),
(558, 'LOS BA&Ntilde;OS DEL INCA', NULL, 54),
(559, 'MAGDALENA', NULL, 54),
(560, 'MATARA', NULL, 54),
(561, 'NAMORA', NULL, 54),
(562, 'SAN JUAN', NULL, 54),
(563, 'CAJABAMBA', NULL, 55),
(564, 'CACHACHI', NULL, 55),
(565, 'CONDEBAMBA', NULL, 55),
(566, 'SITACOCHA', NULL, 55),
(567, 'CELENDIN', NULL, 56),
(568, 'CHUMUCH', NULL, 56),
(569, 'CORTEGANA', NULL, 56),
(570, 'HUASMIN', NULL, 56),
(571, 'JORGE CHAVEZ', NULL, 56),
(572, 'JOSE GALVEZ', NULL, 56),
(573, 'MIGUEL IGLESIAS', NULL, 56),
(574, 'OXAMARCA', NULL, 56),
(575, 'SOROCHUCO', NULL, 56),
(576, 'SUCRE', NULL, 56),
(577, 'UTCO', NULL, 56),
(578, 'LA LIBERTAD DE PALLAN', NULL, 56),
(579, 'CHOTA', NULL, 57),
(580, 'ANGUIA', NULL, 57),
(581, 'CHADIN', NULL, 57),
(582, 'CHIGUIRIP', NULL, 57),
(583, 'CHIMBAN', NULL, 57),
(584, 'CHOROPAMPA', NULL, 57),
(585, 'COCHABAMBA', NULL, 57),
(586, 'CONCHAN', NULL, 57),
(587, 'HUAMBOS', NULL, 57),
(588, 'LAJAS', NULL, 57),
(589, 'LLAMA', NULL, 57),
(590, 'MIRACOSTA', NULL, 57),
(591, 'PACCHA', NULL, 57),
(592, 'PION', NULL, 57),
(593, 'QUEROCOTO', NULL, 57),
(594, 'SAN JUAN DE LICUPIS', NULL, 57),
(595, 'TACABAMBA', NULL, 57),
(596, 'TOCMOCHE', NULL, 57),
(597, 'CHALAMARCA', NULL, 57),
(598, 'CONTUMAZA', NULL, 58),
(599, 'CHILETE', NULL, 58),
(600, 'CUPISNIQUE', NULL, 58),
(601, 'GUZMANGO', NULL, 58),
(602, 'SAN BENITO', NULL, 58),
(603, 'SANTA CRUZ DE TOLED', NULL, 58),
(604, 'TANTARICA', NULL, 58),
(605, 'YONAN', NULL, 58),
(606, 'CUTERVO', NULL, 59),
(607, 'CALLAYUC', NULL, 59),
(608, 'CHOROS', NULL, 59),
(609, 'CUJILLO', NULL, 59),
(610, 'LA RAMADA', NULL, 59),
(611, 'PIMPINGOS', NULL, 59),
(612, 'QUEROCOTILLO', NULL, 59),
(613, 'SAN ANDRES DE CUTERVO', NULL, 59),
(614, 'SAN JUAN DE CUTERVO', NULL, 59),
(615, 'SAN LUIS DE LUCMA', NULL, 59),
(616, 'SANTA CRUZ', NULL, 59),
(617, 'SANTO DOMINGO DE LA CAPILLA', NULL, 59),
(618, 'SANTO TOMAS', NULL, 59),
(619, 'SOCOTA', NULL, 59),
(620, 'TORIBIO CASANOVA', NULL, 59),
(621, 'BAMBAMARCA', NULL, 60),
(622, 'CHUGUR', NULL, 60),
(623, 'HUALGAYOC', NULL, 60),
(624, 'JAEN', NULL, 61),
(625, 'BELLAVISTA', NULL, 61),
(626, 'CHONTALI', NULL, 61),
(627, 'COLASAY', NULL, 61),
(628, 'HUABAL', NULL, 61),
(629, 'LAS PIRIAS', NULL, 61),
(630, 'POMAHUACA', NULL, 61),
(631, 'PUCARA', NULL, 61),
(632, 'SALLIQUE', NULL, 61),
(633, 'SAN FELIPE', NULL, 61),
(634, 'SAN JOSE DEL ALTO', NULL, 61),
(635, 'SANTA ROSA', NULL, 61),
(636, 'SAN IGNACIO', NULL, 62),
(637, 'CHIRINOS', NULL, 62),
(638, 'HUARANGO', NULL, 62),
(639, 'LA COIPA', NULL, 62),
(640, 'NAMBALLE', NULL, 62),
(641, 'SAN JOSE DE LOURDES', NULL, 62),
(642, 'TABACONAS', NULL, 62),
(643, 'PEDRO GALVEZ', NULL, 63),
(644, 'CHANCAY', NULL, 63),
(645, 'EDUARDO VILLANUEVA', NULL, 63),
(646, 'GREGORIO PITA', NULL, 63),
(647, 'ICHOCAN', NULL, 63),
(648, 'JOSE MANUEL QUIROZ', NULL, 63),
(649, 'JOSE SABOGAL', NULL, 63),
(650, 'SAN MIGUEL', NULL, 64),
(651, 'SAN MIGUEL', NULL, 64),
(652, 'BOLIVAR', NULL, 64),
(653, 'CALQUIS', NULL, 64),
(654, 'CATILLUC', NULL, 64),
(655, 'EL PRADO', NULL, 64),
(656, 'LA FLORIDA', NULL, 64),
(657, 'LLAPA', NULL, 64),
(658, 'NANCHOC', NULL, 64),
(659, 'NIEPOS', NULL, 64),
(660, 'SAN GREGORIO', NULL, 64),
(661, 'SAN SILVESTRE DE COCHAN', NULL, 64),
(662, 'TONGOD', NULL, 64),
(663, 'UNION AGUA BLANCA', NULL, 64),
(664, 'SAN PABLO', NULL, 65),
(665, 'SAN BERNARDINO', NULL, 65),
(666, 'SAN LUIS', NULL, 65),
(667, 'TUMBADEN', NULL, 65),
(668, 'SANTA CRUZ', NULL, 66),
(669, 'ANDABAMBA', NULL, 66),
(670, 'CATACHE', NULL, 66),
(671, 'CHANCAYBA&Ntilde;OS', NULL, 66),
(672, 'LA ESPERANZA', NULL, 66),
(673, 'NINABAMBA', NULL, 66),
(674, 'PULAN', NULL, 66),
(675, 'SAUCEPAMPA', NULL, 66),
(676, 'SEXI', NULL, 66),
(677, 'UTICYACU', NULL, 66),
(678, 'YAUYUCAN', NULL, 66),
(679, 'CALLAO', NULL, 67),
(680, 'BELLAVISTA', NULL, 67),
(681, 'CARMEN DE LA LEGUA REYNOSO', NULL, 67),
(682, 'LA PERLA', NULL, 67),
(683, 'LA PUNTA', NULL, 67),
(684, 'VENTANILLA', NULL, 67),
(685, 'CUSCO', NULL, 67),
(686, 'CCORCA', NULL, 67),
(687, 'POROY', NULL, 67),
(688, 'SAN JERONIMO', NULL, 67),
(689, 'SAN SEBASTIAN', NULL, 67),
(690, 'SANTIAGO', NULL, 67),
(691, 'SAYLLA', NULL, 67),
(692, 'WANCHAQ', NULL, 67),
(693, 'ACOMAYO', NULL, 68),
(694, 'ACOPIA', NULL, 68),
(695, 'ACOS', NULL, 68),
(696, 'MOSOC LLACTA', NULL, 68),
(697, 'POMACANCHI', NULL, 68),
(698, 'RONDOCAN', NULL, 68),
(699, 'SANGARARA', NULL, 68),
(700, 'ANTA', NULL, 69),
(701, 'ANCAHUASI', NULL, 69),
(702, 'CACHIMAYO', NULL, 69),
(703, 'CHINCHAYPUJIO', NULL, 69),
(704, 'HUAROCONDO', NULL, 69),
(705, 'LIMATAMBO', NULL, 69),
(706, 'MOLLEPATA', NULL, 69),
(707, 'PUCYURA', NULL, 69),
(708, 'ZURITE', NULL, 69),
(709, 'CALCA', NULL, 70),
(710, 'COYA', NULL, 70),
(711, 'LAMAY', NULL, 70),
(712, 'LARES', NULL, 70),
(713, 'PISAC', NULL, 70),
(714, 'SAN SALVADOR', NULL, 70),
(715, 'TARAY', NULL, 70),
(716, 'YANATILE', NULL, 70),
(717, 'YANAOCA', NULL, 71),
(718, 'CHECCA', NULL, 71),
(719, 'KUNTURKANKI', NULL, 71),
(720, 'LANGUI', NULL, 71),
(721, 'LAYO', NULL, 71),
(722, 'PAMPAMARCA', NULL, 71),
(723, 'QUEHUE', NULL, 71),
(724, 'TUPAC AMARU', NULL, 71),
(725, 'SICUANI', NULL, 72),
(726, 'CHECACUPE', NULL, 72),
(727, 'COMBAPATA', NULL, 72),
(728, 'MARANGANI', NULL, 72),
(729, 'PITUMARCA', NULL, 72),
(730, 'SAN PABLO', NULL, 72),
(731, 'SAN PEDRO', NULL, 72),
(732, 'TINTA', NULL, 72),
(733, 'SANTO TOMAS', NULL, 73),
(734, 'CAPACMARCA', NULL, 73),
(735, 'CHAMACA', NULL, 73),
(736, 'COLQUEMARCA', NULL, 73),
(737, 'LIVITACA', NULL, 73),
(738, 'LLUSCO', NULL, 73),
(739, 'QUI&Ntilde;OTA', NULL, 73),
(740, 'VELILLE', NULL, 73),
(741, 'ESPINAR', NULL, 74),
(742, 'CONDOROMA', NULL, 74),
(743, 'COPORAQUE', NULL, 74),
(744, 'OCORURO', NULL, 74),
(745, 'PALLPATA', NULL, 74),
(746, 'PICHIGUA', NULL, 74),
(747, 'SUYCKUTAMBO', NULL, 74),
(748, 'ALTO PICHIGUA', NULL, 74),
(749, 'SANTA ANA', NULL, 75),
(750, 'ECHARATE', NULL, 75),
(751, 'HUAYOPATA', NULL, 75),
(752, 'MARANURA', NULL, 75),
(753, 'OCOBAMBA', NULL, 75),
(754, 'QUELLOUNO', NULL, 75),
(755, 'KIMBIRI', NULL, 75),
(756, 'SANTA TERESA', NULL, 75),
(757, 'VILCABAMBA', NULL, 75),
(758, 'PICHARI', NULL, 75),
(759, 'PARURO', NULL, 76),
(760, 'ACCHA', NULL, 76),
(761, 'CCAPI', NULL, 76),
(762, 'COLCHA', NULL, 76),
(763, 'HUANOQUITE', NULL, 76),
(764, 'OMACHA', NULL, 76),
(765, 'PACCARITAMBO', NULL, 76),
(766, 'PILLPINTO', NULL, 76),
(767, 'YAURISQUE', NULL, 76),
(768, 'PAUCARTAMBO', NULL, 77),
(769, 'CAICAY', NULL, 77),
(770, 'CHALLABAMBA', NULL, 77),
(771, 'COLQUEPATA', NULL, 77),
(772, 'HUANCARANI', NULL, 77),
(773, 'KOS&Ntilde;IPATA', NULL, 77),
(774, 'URCOS', NULL, 78),
(775, 'ANDAHUAYLILLAS', NULL, 78),
(776, 'CAMANTI', NULL, 78),
(777, 'CCARHUAYO', NULL, 78),
(778, 'CCATCA', NULL, 78),
(779, 'CUSIPATA', NULL, 78),
(780, 'HUARO', NULL, 78),
(781, 'LUCRE', NULL, 78),
(782, 'MARCAPATA', NULL, 78),
(783, 'OCONGATE', NULL, 78),
(784, 'OROPESA', NULL, 78),
(785, 'QUIQUIJANA', NULL, 78),
(786, 'URUBAMBA', NULL, 79),
(787, 'CHINCHERO', NULL, 79),
(788, 'HUAYLLABAMBA', NULL, 79),
(789, 'MACHUPICCHU', NULL, 79),
(790, 'MARAS', NULL, 79),
(791, 'OLLANTAYTAMBO', NULL, 79),
(792, 'YUCAY', NULL, 79),
(793, 'HUANCAVELICA', NULL, 80),
(794, 'ACOBAMBILLA', NULL, 80),
(795, 'ACORIA', NULL, 80),
(796, 'CONAYCA', NULL, 80),
(797, 'CUENCA', NULL, 80),
(798, 'HUACHOCOLPA', NULL, 80),
(799, 'HUAYLLAHUARA', NULL, 80),
(800, 'IZCUCHACA', NULL, 80),
(801, 'LARIA', NULL, 80),
(802, 'MANTA', NULL, 80),
(803, 'MARISCAL CACERES', NULL, 80),
(804, 'MOYA', NULL, 80),
(805, 'NUEVO OCCORO', NULL, 80),
(806, 'PALCA', NULL, 80),
(807, 'PILCHACA', NULL, 80),
(808, 'VILCA', NULL, 80),
(809, 'YAULI', NULL, 80),
(810, 'ASCENSION', NULL, 80),
(811, 'HUANDO', NULL, 80),
(812, 'ACOBAMBA', NULL, 81),
(813, 'ANDABAMBA', NULL, 81),
(814, 'ANTA', NULL, 81),
(815, 'CAJA', NULL, 81),
(816, 'MARCAS', NULL, 81),
(817, 'PAUCARA', NULL, 81),
(818, 'POMACOCHA', NULL, 81),
(819, 'ROSARIO', NULL, 81),
(820, 'LIRCAY', NULL, 82),
(821, 'ANCHONGA', NULL, 82),
(822, 'CALLANMARCA', NULL, 82),
(823, 'CCOCHACCASA', NULL, 82),
(824, 'CHINCHO', NULL, 82),
(825, 'CONGALLA', NULL, 82),
(826, 'HUANCA-HUANCA', NULL, 82),
(827, 'HUAYLLAY GRANDE', NULL, 82),
(828, 'JULCAMARCA', NULL, 82),
(829, 'SAN ANTONIO DE ANTAPARCO', NULL, 82),
(830, 'SANTO TOMAS DE PATA', NULL, 82),
(831, 'SECCLLA', NULL, 82),
(832, 'CASTROVIRREYNA', NULL, 83),
(833, 'ARMA', NULL, 83),
(834, 'AURAHUA', NULL, 83),
(835, 'CAPILLAS', NULL, 83),
(836, 'CHUPAMARCA', NULL, 83),
(837, 'COCAS', NULL, 83),
(838, 'HUACHOS', NULL, 83),
(839, 'HUAMATAMBO', NULL, 83),
(840, 'MOLLEPAMPA', NULL, 83),
(841, 'SAN JUAN', NULL, 83),
(842, 'SANTA ANA', NULL, 83),
(843, 'TANTARA', NULL, 83),
(844, 'TICRAPO', NULL, 83),
(845, 'CHURCAMPA', NULL, 84),
(846, 'ANCO', NULL, 84),
(847, 'CHINCHIHUASI', NULL, 84),
(848, 'EL CARMEN', NULL, 84),
(849, 'LA MERCED', NULL, 84),
(850, 'LOCROJA', NULL, 84),
(851, 'PAUCARBAMBA', NULL, 84),
(852, 'SAN MIGUEL DE MAYOCC', NULL, 84),
(853, 'SAN PEDRO DE CORIS', NULL, 84),
(854, 'PACHAMARCA', NULL, 84),
(855, 'HUAYTARA', NULL, 85),
(856, 'AYAVI', NULL, 85),
(857, 'CORDOVA', NULL, 85),
(858, 'HUAYACUNDO ARMA', NULL, 85),
(859, 'LARAMARCA', NULL, 85),
(860, 'OCOYO', NULL, 85),
(861, 'PILPICHACA', NULL, 85),
(862, 'QUERCO', NULL, 85),
(863, 'QUITO-ARMA', NULL, 85),
(864, 'SAN ANTONIO DE CUSICANCHA', NULL, 85),
(865, 'SAN FRANCISCO DE SANGAYAICO', NULL, 85),
(866, 'SAN ISIDRO', NULL, 85),
(867, 'SANTIAGO DE CHOCORVOS', NULL, 85),
(868, 'SANTIAGO DE QUIRAHUARA', NULL, 85),
(869, 'SANTO DOMINGO DE CAPILLAS', NULL, 85),
(870, 'TAMBO', NULL, 85),
(871, 'PAMPAS', NULL, 86),
(872, 'ACOSTAMBO', NULL, 86),
(873, 'ACRAQUIA', NULL, 86),
(874, 'AHUAYCHA', NULL, 86),
(875, 'COLCABAMBA', NULL, 86),
(876, 'DANIEL HERNANDEZ', NULL, 86),
(877, 'HUACHOCOLPA', NULL, 86),
(878, 'HUARIBAMBA', NULL, 86),
(879, '&Ntilde;AHUIMPUQUIO', NULL, 86),
(880, 'PAZOS', NULL, 86),
(881, 'QUISHUAR', NULL, 86),
(882, 'SALCABAMBA', NULL, 86),
(883, 'SALCAHUASI', NULL, 86),
(884, 'SAN MARCOS DE ROCCHAC', NULL, 86),
(885, 'SURCUBAMBA', NULL, 86),
(886, 'TINTAY PUNCU', NULL, 86),
(887, 'HUANUCO', NULL, 87),
(888, 'AMARILIS', NULL, 87),
(889, 'CHINCHAO', NULL, 87),
(890, 'CHURUBAMBA', NULL, 87),
(891, 'MARGOS', NULL, 87),
(892, 'QUISQUI', NULL, 87),
(893, 'SAN FRANCISCO DE CAYRAN', NULL, 87),
(894, 'SAN PEDRO DE CHAULAN', NULL, 87),
(895, 'SANTA MARIA DEL VALLE', NULL, 87),
(896, 'YARUMAYO', NULL, 87),
(897, 'PILLCO MARCA', NULL, 87),
(898, 'AMBO', NULL, 88),
(899, 'CAYNA', NULL, 88),
(900, 'COLPAS', NULL, 88),
(901, 'CONCHAMARCA', NULL, 88),
(902, 'HUACAR', NULL, 88),
(903, 'SAN FRANCISCO', NULL, 88),
(904, 'SAN RAFAEL', NULL, 88),
(905, 'TOMAY KICHWA', NULL, 88),
(906, 'LA UNION', NULL, 89),
(907, 'CHUQUIS', NULL, 89),
(908, 'MARIAS', NULL, 89),
(909, 'PACHAS', NULL, 89),
(910, 'QUIVILLA', NULL, 89),
(911, 'RIPAN', NULL, 89),
(912, 'SHUNQUI', NULL, 89),
(913, 'SILLAPATA', NULL, 89),
(914, 'YANAS', NULL, 89),
(915, 'HUACAYBAMBA', NULL, 90),
(916, 'CANCHABAMBA', NULL, 90),
(917, 'COCHABAMBA', NULL, 90),
(918, 'PINRA', NULL, 90),
(919, 'LLATA', NULL, 91),
(920, 'ARANCAY', NULL, 91),
(921, 'CHAVIN DE PARIARCA', NULL, 91),
(922, 'JACAS GRANDE', NULL, 91),
(923, 'JIRCAN', NULL, 91),
(924, 'MIRAFLORES', NULL, 91),
(925, 'MONZON', NULL, 91),
(926, 'PUNCHAO', NULL, 91),
(927, 'PU&Ntilde;OS', NULL, 91),
(928, 'SINGA', NULL, 91),
(929, 'TANTAMAYO', NULL, 91),
(930, 'RUPA-RUPA', NULL, 92),
(931, 'DANIEL ALOMIA ROBLES', NULL, 92),
(932, 'HERMILIO VALDIZAN', NULL, 92),
(933, 'JOSE CRESPO Y CASTILLO', NULL, 92),
(934, 'LUYANDO', NULL, 92),
(935, 'MARIANO DAMASO BERAUN', NULL, 92),
(936, 'HUACRACHUCO', NULL, 93),
(937, 'CHOLON', NULL, 93),
(938, 'SAN BUENAVENTURA', NULL, 93),
(939, 'PANAO', NULL, 94),
(940, 'CHAGLLA', NULL, 94),
(941, 'MOLINO', NULL, 94),
(942, 'UMARI', NULL, 94),
(943, 'PUERTO INCA', NULL, 95),
(944, 'CODO DEL POZUZO', NULL, 95),
(945, 'HONORIA', NULL, 95),
(946, 'TOURNAVISTA', NULL, 95),
(947, 'YUYAPICHIS', NULL, 95),
(948, 'JESUS', NULL, 96),
(949, 'BA&Ntilde;OS', NULL, 96),
(950, 'JIVIA', NULL, 96),
(951, 'QUEROPALCA', NULL, 96),
(952, 'RONDOS', NULL, 96),
(953, 'SAN FRANCISCO DE ASIS', NULL, 96),
(954, 'SAN MIGUEL DE CAURI', NULL, 96),
(955, 'CHAVINILLO', NULL, 97),
(956, 'CAHUAC', NULL, 97),
(957, 'CHACABAMBA', NULL, 97),
(958, 'APARICIO POMARES', NULL, 97),
(959, 'JACAS CHICO', NULL, 97),
(960, 'OBAS', NULL, 97),
(961, 'PAMPAMARCA', NULL, 97),
(962, 'CHORAS', NULL, 97),
(963, 'ICA', NULL, 98),
(964, 'LA TINGUI&Ntilde;A', NULL, 98),
(965, 'LOS AQUIJES', NULL, 98),
(966, 'OCUCAJE', NULL, 98),
(967, 'PACHACUTEC', NULL, 98),
(968, 'PARCONA', NULL, 98),
(969, 'PUEBLO NUEVO', NULL, 98),
(970, 'SALAS', NULL, 98),
(971, 'SAN JOSE DE LOS MOLINOS', NULL, 98),
(972, 'SAN JUAN BAUTISTA', NULL, 98),
(973, 'SANTIAGO', NULL, 98),
(974, 'SUBTANJALLA', NULL, 98),
(975, 'TATE', NULL, 98),
(976, 'YAUCA DEL ROSARIO', NULL, 98),
(977, 'CHINCHA ALTA', NULL, 99),
(978, 'ALTO LARAN', NULL, 99),
(979, 'CHAVIN', NULL, 99),
(980, 'CHINCHA BAJA', NULL, 99),
(981, 'EL CARMEN', NULL, 99),
(982, 'GROCIO PRADO', NULL, 99),
(983, 'PUEBLO NUEVO', NULL, 99),
(984, 'SAN JUAN DE YANAC', NULL, 99),
(985, 'SAN PEDRO DE HUACARPANA', NULL, 99),
(986, 'SUNAMPE', NULL, 99),
(987, 'TAMBO DE MORA', NULL, 99),
(988, 'NAZCA', NULL, 100),
(989, 'CHANGUILLO', NULL, 100),
(990, 'EL INGENIO', NULL, 100),
(991, 'MARCONA', NULL, 100),
(992, 'VISTA ALEGRE', NULL, 100),
(993, 'PALPA', NULL, 101),
(994, 'LLIPATA', NULL, 101),
(995, 'RIO GRANDE', NULL, 101),
(996, 'SANTA CRUZ', NULL, 101),
(997, 'TIBILLO', NULL, 101),
(998, 'PISCO', NULL, 102),
(999, 'HUANCANO', NULL, 102),
(1000, 'HUMAY', NULL, 102),
(1001, 'INDEPENDENCIA', NULL, 102),
(1002, 'PARACAS', NULL, 102),
(1003, 'SAN ANDRES', NULL, 102),
(1004, 'SAN CLEMENTE', NULL, 102),
(1005, 'TUPAC AMARU INCA', NULL, 102),
(1006, 'HUANCAYO', NULL, 103),
(1007, 'CARHUACALLANGA', NULL, 103),
(1008, 'CHACAPAMPA', NULL, 103),
(1009, 'CHICCHE', NULL, 103),
(1010, 'CHILCA', NULL, 103),
(1011, 'CHONGOS ALTO', NULL, 103),
(1012, 'CHUPURO', NULL, 103),
(1013, 'COLCA', NULL, 103),
(1014, 'CULLHUAS', NULL, 103),
(1015, 'EL TAMBO', NULL, 103),
(1016, 'HUACRAPUQUIO', NULL, 103),
(1017, 'HUALHUAS', NULL, 103),
(1018, 'HUANCAN', NULL, 103),
(1019, 'HUASICANCHA', NULL, 103),
(1020, 'HUAYUCACHI', NULL, 103),
(1021, 'INGENIO', NULL, 103),
(1022, 'PARIAHUANCA', NULL, 103),
(1023, 'PILCOMAYO', NULL, 103),
(1024, 'PUCARA', NULL, 103),
(1025, 'QUICHUAY', NULL, 103),
(1026, 'QUILCAS', NULL, 103),
(1027, 'SAN AGUSTIN', NULL, 103),
(1028, 'SAN JERONIMO DE TUNAN', NULL, 103),
(1029, 'SA&Ntilde;O', NULL, 103),
(1030, 'SAPALLANGA', NULL, 103),
(1031, 'SICAYA', NULL, 103),
(1032, 'SANTO DOMINGO DE ACOBAMBA', NULL, 103),
(1033, 'VIQUES', NULL, 103),
(1034, 'CONCEPCION', NULL, 104),
(1035, 'ACO', NULL, 104),
(1036, 'ANDAMARCA', NULL, 104),
(1037, 'CHAMBARA', NULL, 104),
(1038, 'COCHAS', NULL, 104),
(1039, 'COMAS', NULL, 104),
(1040, 'HEROINAS TOLEDO', NULL, 104),
(1041, 'MANZANARES', NULL, 104),
(1042, 'MARISCAL CASTILLA', NULL, 104),
(1043, 'MATAHUASI', NULL, 104),
(1044, 'MITO', NULL, 104),
(1045, 'NUEVE DE JULIO', NULL, 104),
(1046, 'ORCOTUNA', NULL, 104),
(1047, 'SAN JOSE DE QUERO', NULL, 104),
(1048, 'SANTA ROSA DE OCOPA', NULL, 104),
(1049, 'CHANCHAMAYO', NULL, 105),
(1050, 'PERENE', NULL, 105),
(1051, 'PICHANAQUI', NULL, 105),
(1052, 'SAN LUIS DE SHUARO', NULL, 105),
(1053, 'SAN RAMON', NULL, 105),
(1054, 'VITOC', NULL, 105),
(1055, 'JAUJA', NULL, 106),
(1056, 'ACOLLA', NULL, 106),
(1057, 'APATA', NULL, 106),
(1058, 'ATAURA', NULL, 106),
(1059, 'CANCHAYLLO', NULL, 106),
(1060, 'CURICACA', NULL, 106),
(1061, 'EL MANTARO', NULL, 106),
(1062, 'HUAMALI', NULL, 106),
(1063, 'HUARIPAMPA', NULL, 106),
(1064, 'HUERTAS', NULL, 106),
(1065, 'JANJAILLO', NULL, 106),
(1066, 'JULCAN', NULL, 106),
(1067, 'LEONOR ORDO&Ntilde;EZ', NULL, 106),
(1068, 'LLOCLLAPAMPA', NULL, 106),
(1069, 'MARCO', NULL, 106),
(1070, 'MASMA', NULL, 106),
(1071, 'MASMA CHICCHE', NULL, 106),
(1072, 'MOLINOS', NULL, 106),
(1073, 'MONOBAMBA', NULL, 106),
(1074, 'MUQUI', NULL, 106),
(1075, 'MUQUIYAUYO', NULL, 106),
(1076, 'PACA', NULL, 106),
(1077, 'PACCHA', NULL, 106),
(1078, 'PANCAN', NULL, 106),
(1079, 'PARCO', NULL, 106),
(1080, 'POMACANCHA', NULL, 106),
(1081, 'RICRAN', NULL, 106),
(1082, 'SAN LORENZO', NULL, 106),
(1083, 'SAN PEDRO DE CHUNAN', NULL, 106),
(1084, 'SAUSA', NULL, 106),
(1085, 'SINCOS', NULL, 106),
(1086, 'TUNAN MARCA', NULL, 106),
(1087, 'YAULI', NULL, 106),
(1088, 'YAUYOS', NULL, 106),
(1089, 'JUNIN', NULL, 107),
(1090, 'CARHUAMAYO', NULL, 107),
(1091, 'ONDORES', NULL, 107),
(1092, 'ULCUMAYO', NULL, 107),
(1093, 'SATIPO', NULL, 108),
(1094, 'COVIRIALI', NULL, 108),
(1095, 'LLAYLLA', NULL, 108),
(1096, 'MAZAMARI', NULL, 108),
(1097, 'PAMPA HERMOSA', NULL, 108),
(1098, 'PANGOA', NULL, 108),
(1099, 'RIO NEGRO', NULL, 108),
(1100, 'RIO TAMBO', NULL, 108),
(1101, 'TARMA', NULL, 109),
(1102, 'ACOBAMBA', NULL, 109),
(1103, 'HUARICOLCA', NULL, 109),
(1104, 'HUASAHUASI', NULL, 109),
(1105, 'LA UNION', NULL, 109),
(1106, 'PALCA', NULL, 109),
(1107, 'PALCAMAYO', NULL, 109),
(1108, 'SAN PEDRO DE CAJAS', NULL, 109),
(1109, 'TAPO', NULL, 109),
(1110, 'LA OROYA', NULL, 110),
(1111, 'CHACAPALPA', NULL, 110),
(1112, 'HUAY-HUAY', NULL, 110),
(1113, 'MARCAPOMACOCHA', NULL, 110),
(1114, 'MOROCOCHA', NULL, 110),
(1115, 'PACCHA', NULL, 110),
(1116, 'SANTA BARBARA DE CARHUACAYAN', NULL, 110),
(1117, 'SANTA ROSA DE SACCO', NULL, 110),
(1118, 'SUITUCANCHA', NULL, 110),
(1119, 'YAULI', NULL, 110),
(1120, 'CHUPACA', NULL, 111),
(1121, 'AHUAC', NULL, 111),
(1122, 'CHONGOS BAJO', NULL, 111),
(1123, 'HUACHAC', NULL, 111),
(1124, 'HUAMANCACA CHICO', NULL, 111),
(1125, 'SAN JUAN DE ISCOS', NULL, 111),
(1126, 'SAN JUAN DE JARPA', NULL, 111),
(1127, 'TRES DE DICIEMBRE', NULL, 111),
(1128, 'YANACANCHA', NULL, 111),
(1129, 'TRUJILLO', NULL, 112),
(1130, 'EL PORVENIR', NULL, 112),
(1131, 'FLORENCIA DE MORA', NULL, 112),
(1132, 'HUANCHACO', NULL, 112),
(1133, 'LA ESPERANZA', NULL, 112),
(1134, 'LAREDO', NULL, 112),
(1135, 'MOCHE', NULL, 112),
(1136, 'POROTO', NULL, 112),
(1137, 'SALAVERRY', NULL, 112),
(1138, 'SIMBAL', NULL, 112),
(1139, 'VICTOR LARCO HERRERA', NULL, 112),
(1140, 'ASCOPE', NULL, 113),
(1141, 'CHICAMA', NULL, 113),
(1142, 'CHOCOPE', NULL, 113),
(1143, 'MAGDALENA DE CAO', NULL, 113),
(1144, 'PAIJAN', NULL, 113),
(1145, 'RAZURI', NULL, 113),
(1146, 'SANTIAGO DE CAO', NULL, 113),
(1147, 'CASA GRANDE', NULL, 113),
(1148, 'BOLIVAR', NULL, 114),
(1149, 'BAMBAMARCA', NULL, 114),
(1150, 'CONDORMARCA', NULL, 114),
(1151, 'LONGOTEA', NULL, 114),
(1152, 'UCHUMARCA', NULL, 114),
(1153, 'UCUNCHA', NULL, 114),
(1154, 'CHEPEN', NULL, 115),
(1155, 'PACANGA', NULL, 115),
(1156, 'PUEBLO NUEVO', NULL, 115),
(1157, 'JULCAN', NULL, 116),
(1158, 'CALAMARCA', NULL, 116),
(1159, 'CARABAMBA', NULL, 116),
(1160, 'HUASO', NULL, 116),
(1161, 'OTUZCO', NULL, 117),
(1162, 'AGALLPAMPA', NULL, 117),
(1163, 'CHARAT', NULL, 117),
(1164, 'HUARANCHAL', NULL, 117),
(1165, 'LA CUESTA', NULL, 117),
(1166, 'MACHE', NULL, 117),
(1167, 'PARANDAY', NULL, 117),
(1168, 'SALPO', NULL, 117),
(1169, 'SINSICAP', NULL, 117),
(1170, 'USQUIL', NULL, 117),
(1171, 'SAN PEDRO DE LLOC', NULL, 118),
(1172, 'GUADALUPE', NULL, 118),
(1173, 'JEQUETEPEQUE', NULL, 118),
(1174, 'PACASMAYO', NULL, 118),
(1175, 'SAN JOSE', NULL, 118),
(1176, 'TAYABAMBA', NULL, 119),
(1177, 'BULDIBUYO', NULL, 119),
(1178, 'CHILLIA', NULL, 119),
(1179, 'HUANCASPATA', NULL, 119),
(1180, 'HUAYLILLAS', NULL, 119),
(1181, 'HUAYO', NULL, 119),
(1182, 'ONGON', NULL, 119),
(1183, 'PARCOY', NULL, 119),
(1184, 'PATAZ', NULL, 119),
(1185, 'PIAS', NULL, 119),
(1186, 'SANTIAGO DE CHALLAS', NULL, 119),
(1187, 'TAURIJA', NULL, 119),
(1188, 'URPAY', NULL, 119),
(1189, 'HUAMACHUCO', NULL, 120),
(1190, 'CHUGAY', NULL, 120),
(1191, 'COCHORCO', NULL, 120),
(1192, 'CURGOS', NULL, 120),
(1193, 'MARCABAL', NULL, 120),
(1194, 'SANAGORAN', NULL, 120),
(1195, 'SARIN', NULL, 120),
(1196, 'SARTIMBAMBA', NULL, 120),
(1197, 'SANTIAGO DE CHUCO', NULL, 121),
(1198, 'ANGASMARCA', NULL, 121),
(1199, 'CACHICADAN', NULL, 121),
(1200, 'MOLLEBAMBA', NULL, 121),
(1201, 'MOLLEPATA', NULL, 121),
(1202, 'QUIRUVILCA', NULL, 121),
(1203, 'SANTA CRUZ DE CHUCA', NULL, 121),
(1204, 'SITABAMBA', NULL, 121),
(1205, 'GRAN CHIMU', NULL, 122),
(1206, 'CASCAS', NULL, 122),
(1207, 'LUCMA', NULL, 122),
(1208, 'MARMOT', NULL, 122),
(1209, 'SAYAPULLO', NULL, 122),
(1210, 'VIRU', NULL, 123),
(1211, 'CHAO', NULL, 123),
(1212, 'GUADALUPITO', NULL, 123),
(1213, 'CHICLAYO', NULL, 124),
(1214, 'CHONGOYAPE', NULL, 124),
(1215, 'ETEN', NULL, 124),
(1216, 'ETEN PUERTO', NULL, 124),
(1217, 'JOSE LEONARDO ORTIZ', NULL, 124),
(1218, 'LA VICTORIA', NULL, 124),
(1219, 'LAGUNAS', NULL, 124),
(1220, 'MONSEFU', NULL, 124),
(1221, 'NUEVA ARICA', NULL, 124),
(1222, 'OYOTUN', NULL, 124),
(1223, 'PICSI', NULL, 124),
(1224, 'PIMENTEL', NULL, 124),
(1225, 'REQUE', NULL, 124),
(1226, 'SANTA ROSA', NULL, 124),
(1227, 'SA&Ntilde;A', NULL, 124),
(1228, 'CAYALTI', NULL, 124),
(1229, 'PATAPO', NULL, 124),
(1230, 'POMALCA', NULL, 124),
(1231, 'PUCALA', NULL, 124),
(1232, 'TUMAN', NULL, 124),
(1233, 'FERRE&Ntilde;AFE', NULL, 125),
(1234, 'CA&Ntilde;ARIS', NULL, 125),
(1235, 'INCAHUASI', NULL, 125),
(1236, 'MANUEL ANTONIO MESONES MURO', NULL, 125),
(1237, 'PITIPO', NULL, 125),
(1238, 'PUEBLO NUEVO', NULL, 125),
(1239, 'LAMBAYEQUE', NULL, 126),
(1240, 'CHOCHOPE', NULL, 126),
(1241, 'ILLIMO', NULL, 126),
(1242, 'JAYANCA', NULL, 126),
(1243, 'MOCHUMI', NULL, 126),
(1244, 'MORROPE', NULL, 126),
(1245, 'MOTUPE', NULL, 126),
(1246, 'OLMOS', NULL, 126),
(1247, 'PACORA', NULL, 126),
(1248, 'SALAS', NULL, 126),
(1249, 'SAN JOSE', NULL, 126),
(1250, 'TUCUME', NULL, 126),
(1251, 'LIMA', NULL, 127),
(1252, 'ANCON', NULL, 127),
(1253, 'ATE', NULL, 127),
(1254, 'BARRANCO', NULL, 127),
(1255, 'BRE&Ntilde;A', NULL, 127),
(1256, 'CARABAYLLO', NULL, 127),
(1257, 'CHACLACAYO', NULL, 127),
(1258, 'CHORRILLOS', NULL, 127),
(1259, 'CIENEGUILLA', NULL, 127),
(1260, 'COMAS', NULL, 127),
(1261, 'EL AGUSTINO', NULL, 127),
(1262, 'INDEPENDENCIA', NULL, 127),
(1263, 'JESUS MARIA', NULL, 127),
(1264, 'LA MOLINA', NULL, 127),
(1265, 'LA VICTORIA', NULL, 127),
(1266, 'LINCE', NULL, 127),
(1267, 'LOS OLIVOS', NULL, 127),
(1268, 'LURIGANCHO', NULL, 127),
(1269, 'LURIN', NULL, 127),
(1270, 'MAGDALENA DEL MAR', NULL, 127),
(1271, 'MAGDALENA VIEJA', NULL, 127),
(1272, 'MIRAFLORES', NULL, 127),
(1273, 'PACHACAMAC', NULL, 127),
(1274, 'PUCUSANA', NULL, 127),
(1275, 'PUENTE PIEDRA', NULL, 127),
(1276, 'PUNTA HERMOSA', NULL, 127),
(1277, 'PUNTA NEGRA', NULL, 127),
(1278, 'RIMAC', NULL, 127),
(1279, 'SAN BARTOLO', NULL, 127),
(1280, 'SAN BORJA', NULL, 127),
(1281, 'SAN ISIDRO', NULL, 127),
(1282, 'SAN JUAN DE LURIGANCHO', NULL, 127),
(1283, 'SAN JUAN DE MIRAFLORES', NULL, 127),
(1284, 'SAN LUIS', NULL, 127),
(1285, 'SAN MARTIN DE PORRES', NULL, 127),
(1286, 'SAN MIGUEL', NULL, 127),
(1287, 'SANTA ANITA', NULL, 127),
(1288, 'SANTA MARIA DEL MAR', NULL, 127),
(1289, 'SANTA ROSA', NULL, 127),
(1290, 'SANTIAGO DE SURCO', NULL, 127),
(1291, 'SURQUILLO', NULL, 127),
(1292, 'VILLA EL SALVADOR', NULL, 127),
(1293, 'VILLA MARIA DEL TRIUNFO', NULL, 127),
(1294, 'BARRANCA', NULL, 128),
(1295, 'PARAMONGA', NULL, 128),
(1296, 'PATIVILCA', NULL, 128),
(1297, 'SUPE', NULL, 128),
(1298, 'SUPE PUERTO', NULL, 128),
(1299, 'CAJATAMBO', NULL, 129),
(1300, 'COPA', NULL, 129),
(1301, 'GORGOR', NULL, 129),
(1302, 'HUANCAPON', NULL, 129),
(1303, 'MANAS', NULL, 129),
(1304, 'CANTA', NULL, 130),
(1305, 'ARAHUAY', NULL, 130),
(1306, 'HUAMANTANGA', NULL, 130),
(1307, 'HUAROS', NULL, 130),
(1308, 'LACHAQUI', NULL, 130),
(1309, 'SAN BUENAVENTURA', NULL, 130),
(1310, 'SANTA ROSA DE QUIVES', NULL, 130),
(1311, 'SAN VICENTE DE CA&Ntilde;ETE', NULL, 131),
(1312, 'ASIA', NULL, 131),
(1313, 'CALANGO', NULL, 131),
(1314, 'CERRO AZUL', NULL, 131),
(1315, 'CHILCA', NULL, 131),
(1316, 'COAYLLO', NULL, 131),
(1317, 'IMPERIAL', NULL, 131),
(1318, 'LUNAHUANA', NULL, 131),
(1319, 'MALA', NULL, 131),
(1320, 'NUEVO IMPERIAL', NULL, 131),
(1321, 'PACARAN', NULL, 131),
(1322, 'QUILMANA', NULL, 131),
(1323, 'SAN ANTONIO', NULL, 131),
(1324, 'SAN LUIS', NULL, 131),
(1325, 'SANTA CRUZ DE FLORES', NULL, 131),
(1326, 'ZU&Ntilde;IGA', NULL, 131),
(1327, 'HUARAL', NULL, 132),
(1328, 'ATAVILLOS ALTO', NULL, 132),
(1329, 'ATAVILLOS BAJO', NULL, 132),
(1330, 'AUCALLAMA', NULL, 132),
(1331, 'CHANCAY', NULL, 132),
(1332, 'IHUARI', NULL, 132),
(1333, 'LAMPIAN', NULL, 132),
(1334, 'PACARAOS', NULL, 132),
(1335, 'SAN MIGUEL DE ACOS', NULL, 132),
(1336, 'SANTA CRUZ DE ANDAMARCA', NULL, 132),
(1337, 'SUMBILCA', NULL, 132),
(1338, 'VEINTISIETE DE NOVIEMBRE', NULL, 132),
(1339, 'MATUCANA', NULL, 133),
(1340, 'ANTIOQUIA', NULL, 133),
(1341, 'CALLAHUANCA', NULL, 133),
(1342, 'CARAMPOMA', NULL, 133),
(1343, 'CHICLA', NULL, 133),
(1344, 'CUENCA', NULL, 133),
(1345, 'HUACHUPAMPA', NULL, 133),
(1346, 'HUANZA', NULL, 133),
(1347, 'HUAROCHIRI', NULL, 133),
(1348, 'LAHUAYTAMBO', NULL, 133),
(1349, 'LANGA', NULL, 133),
(1350, 'LARAOS', NULL, 133),
(1351, 'MARIATANA', NULL, 133),
(1352, 'RICARDO PALMA', NULL, 133),
(1353, 'SAN ANDRES DE TUPICOCHA', NULL, 133),
(1354, 'SAN ANTONIO', NULL, 133),
(1355, 'SAN BARTOLOME', NULL, 133),
(1356, 'SAN DAMIAN', NULL, 133),
(1357, 'SAN JUAN DE IRIS', NULL, 133),
(1358, 'SAN JUAN DE TANTARANCHE', NULL, 133),
(1359, 'SAN LORENZO DE QUINTI', NULL, 133),
(1360, 'SAN MATEO', NULL, 133),
(1361, 'SAN MATEO DE OTAO', NULL, 133),
(1362, 'SAN PEDRO DE CASTA', NULL, 133),
(1363, 'SAN PEDRO DE HUANCAYRE', NULL, 133),
(1364, 'SANGALLAYA', NULL, 133),
(1365, 'SANTA CRUZ DE COCACHACRA', NULL, 133),
(1366, 'SANTA EULALIA', NULL, 133),
(1367, 'SANTIAGO DE ANCHUCAYA', NULL, 133),
(1368, 'SANTIAGO DE TUNA', NULL, 133),
(1369, 'SANTO DOMINGO DE LOS OLLEROS', NULL, 133),
(1370, 'SURCO', NULL, 133),
(1371, 'HUACHO', NULL, 134),
(1372, 'AMBAR', NULL, 134),
(1373, 'CALETA DE CARQUIN', NULL, 134),
(1374, 'CHECRAS', NULL, 134),
(1375, 'HUALMAY', NULL, 134),
(1376, 'HUAURA', NULL, 134),
(1377, 'LEONCIO PRADO', NULL, 134),
(1378, 'PACCHO', NULL, 134),
(1379, 'SANTA LEONOR', NULL, 134),
(1380, 'SANTA MARIA', NULL, 134),
(1381, 'SAYAN', NULL, 134),
(1382, 'VEGUETA', NULL, 134),
(1383, 'OYON', NULL, 135),
(1384, 'ANDAJES', NULL, 135),
(1385, 'CAUJUL', NULL, 135),
(1386, 'COCHAMARCA', NULL, 135),
(1387, 'NAVAN', NULL, 135),
(1388, 'PACHANGARA', NULL, 135),
(1389, 'YAUYOS', NULL, 136),
(1390, 'ALIS', NULL, 136),
(1391, 'AYAUCA', NULL, 136),
(1392, 'AYAVIRI', NULL, 136),
(1393, 'AZANGARO', NULL, 136),
(1394, 'CACRA', NULL, 136),
(1395, 'CARANIA', NULL, 136),
(1396, 'CATAHUASI', NULL, 136),
(1397, 'CHOCOS', NULL, 136),
(1398, 'COCHAS', NULL, 136),
(1399, 'COLONIA', NULL, 136),
(1400, 'HONGOS', NULL, 136),
(1401, 'HUAMPARA', NULL, 136),
(1402, 'HUANCAYA', NULL, 136),
(1403, 'HUANGASCAR', NULL, 136),
(1404, 'HUANTAN', NULL, 136),
(1405, 'HUA&Ntilde;EC', NULL, 136),
(1406, 'LARAOS', NULL, 136),
(1407, 'LINCHA', NULL, 136),
(1408, 'MADEAN', NULL, 136),
(1409, 'MIRAFLORES', NULL, 136),
(1410, 'OMAS', NULL, 136),
(1411, 'PUTINZA', NULL, 136),
(1412, 'QUINCHES', NULL, 136),
(1413, 'QUINOCAY', NULL, 136),
(1414, 'SAN JOAQUIN', NULL, 136),
(1415, 'SAN PEDRO DE PILAS', NULL, 136),
(1416, 'TANTA', NULL, 136),
(1417, 'TAURIPAMPA', NULL, 136),
(1418, 'TOMAS', NULL, 136),
(1419, 'TUPE', NULL, 136),
(1420, 'VI&Ntilde;AC', NULL, 136),
(1421, 'VITIS', NULL, 136),
(1422, 'IQUITOS', NULL, 137),
(1423, 'ALTO NANAY', NULL, 137),
(1424, 'FERNANDO LORES', NULL, 137),
(1425, 'INDIANA', NULL, 137),
(1426, 'LAS AMAZONAS', NULL, 137),
(1427, 'MAZAN', NULL, 137),
(1428, 'NAPO', NULL, 137),
(1429, 'PUNCHANA', NULL, 137),
(1430, 'PUTUMAYO', NULL, 137),
(1431, 'TORRES CAUSANA', NULL, 137),
(1432, 'BELEN', NULL, 137),
(1433, 'SAN JUAN BAUTISTA', NULL, 137),
(1434, 'YURIMAGUAS', NULL, 138),
(1435, 'BALSAPUERTO', NULL, 138),
(1436, 'BARRANCA', NULL, 138),
(1437, 'CAHUAPANAS', NULL, 138),
(1438, 'JEBEROS', NULL, 138),
(1439, 'LAGUNAS', NULL, 138),
(1440, 'MANSERICHE', NULL, 138),
(1441, 'MORONA', NULL, 138),
(1442, 'PASTAZA', NULL, 138),
(1443, 'SANTA CRUZ', NULL, 138),
(1444, 'TENIENTE CESAR LOPEZ ROJAS', NULL, 138),
(1445, 'NAUTA', NULL, 139),
(1446, 'PARINARI', NULL, 139),
(1447, 'TIGRE', NULL, 139),
(1448, 'TROMPETEROS', NULL, 139),
(1449, 'URARINAS', NULL, 139),
(1450, 'RAMON CASTILLA', NULL, 140),
(1451, 'PEBAS', NULL, 140),
(1452, 'YAVARI', NULL, 140),
(1453, 'SAN PABLO', NULL, 140),
(1454, 'REQUENA', NULL, 141),
(1455, 'ALTO TAPICHE', NULL, 141),
(1456, 'CAPELO', NULL, 141),
(1457, 'EMILIO SAN MARTIN', NULL, 141),
(1458, 'MAQUIA', NULL, 141),
(1459, 'PUINAHUA', NULL, 141),
(1460, 'SAQUENA', NULL, 141),
(1461, 'SOPLIN', NULL, 141),
(1462, 'TAPICHE', NULL, 141),
(1463, 'JENARO HERRERA', NULL, 141),
(1464, 'YAQUERANA', NULL, 141),
(1465, 'CONTAMANA', NULL, 142),
(1466, 'INAHUAYA', NULL, 142),
(1467, 'PADRE MARQUEZ', NULL, 142),
(1468, 'PAMPA HERMOSA', NULL, 142),
(1469, 'SARAYACU', NULL, 142),
(1470, 'VARGAS GUERRA', NULL, 142),
(1471, 'TAMBOPATA', NULL, 143),
(1472, 'INAMBARI', NULL, 143),
(1473, 'LAS PIEDRAS', NULL, 143),
(1474, 'LABERINTO', NULL, 143),
(1475, 'MANU', NULL, 144),
(1476, 'FITZCARRALD', NULL, 144),
(1477, 'MADRE DE DIOS', NULL, 144),
(1478, 'HUEPETUHE', NULL, 144),
(1479, 'I&Ntilde;APARI', NULL, 145),
(1480, 'IBERIA', NULL, 145),
(1481, 'TAHUAMANU', NULL, 145),
(1482, 'MOQUEGUA', NULL, 146),
(1483, 'CARUMAS', NULL, 146),
(1484, 'CUCHUMBAYA', NULL, 146),
(1485, 'SAMEGUA', NULL, 146),
(1486, 'SAN CRISTOBAL', NULL, 146),
(1487, 'TORATA', NULL, 146),
(1488, 'OMATE', NULL, 147),
(1489, 'CHOJATA', NULL, 147),
(1490, 'COALAQUE', NULL, 147),
(1491, 'ICHU&Ntilde;A', NULL, 147),
(1492, 'LA CAPILLA', NULL, 147),
(1493, 'LLOQUE', NULL, 147),
(1494, 'MATALAQUE', NULL, 147),
(1495, 'PUQUINA', NULL, 147),
(1496, 'QUINISTAQUILLAS', NULL, 147),
(1497, 'UBINAS', NULL, 147),
(1498, 'YUNGA', NULL, 147),
(1499, 'ILO', NULL, 148),
(1500, 'EL ALGARROBAL', NULL, 148),
(1501, 'PACOCHA', NULL, 148),
(1502, 'CHAUPIMARCA', NULL, 149),
(1503, 'HUACHON', NULL, 149),
(1504, 'HUARIACA', NULL, 149),
(1505, 'HUAYLLAY', NULL, 149),
(1506, 'NINACACA', NULL, 149),
(1507, 'PALLANCHACRA', NULL, 149),
(1508, 'PAUCARTAMBO', NULL, 149),
(1509, 'SAN FCO.DE ASIS DE YARUSYACAN', NULL, 149),
(1510, 'SIMON BOLIVAR', NULL, 149),
(1511, 'TICLACAYAN', NULL, 149),
(1512, 'TINYAHUARCO', NULL, 149),
(1513, 'VICCO', NULL, 149),
(1514, 'YANACANCHA', NULL, 149),
(1515, 'YANAHUANCA', NULL, 150),
(1516, 'CHACAYAN', NULL, 150),
(1517, 'GOYLLARISQUIZGA', NULL, 150),
(1518, 'PAUCAR', NULL, 150),
(1519, 'SAN PEDRO DE PILLAO', NULL, 150),
(1520, 'SANTA ANA DE TUSI', NULL, 150),
(1521, 'TAPUC', NULL, 150),
(1522, 'VILCABAMBA', NULL, 150),
(1523, 'OXAPAMPA', NULL, 151),
(1524, 'CHONTABAMBA', NULL, 151),
(1525, 'HUANCABAMBA', NULL, 151),
(1526, 'PALCAZU', NULL, 151),
(1527, 'POZUZO', NULL, 151),
(1528, 'PUERTO BERMUDEZ', NULL, 151),
(1529, 'VILLA RICA', NULL, 151),
(1530, 'PIURA', NULL, 152),
(1531, 'CASTILLA', NULL, 152),
(1532, 'CATACAOS', NULL, 152),
(1533, 'CURA MORI', NULL, 152),
(1534, 'EL TALLAN', NULL, 152),
(1535, 'LA ARENA', NULL, 152),
(1536, 'LA UNION', NULL, 152),
(1537, 'LAS LOMAS', NULL, 152),
(1538, 'TAMBO GRANDE', NULL, 152),
(1539, 'AYABACA', NULL, 153),
(1540, 'FRIAS', NULL, 153),
(1541, 'JILILI', NULL, 153),
(1542, 'LAGUNAS', NULL, 153),
(1543, 'MONTERO', NULL, 153),
(1544, 'PACAIPAMPA', NULL, 153),
(1545, 'PAIMAS', NULL, 153),
(1546, 'SAPILLICA', NULL, 153),
(1547, 'SICCHEZ', NULL, 153),
(1548, 'SUYO', NULL, 153),
(1549, 'HUANCABAMBA', NULL, 154),
(1550, 'CANCHAQUE', NULL, 154),
(1551, 'EL CARMEN DE LA FRONTERA', NULL, 154),
(1552, 'HUARMACA', NULL, 154),
(1553, 'LALAQUIZ', NULL, 154),
(1554, 'SAN MIGUEL DE EL FAIQUE', NULL, 154),
(1555, 'SONDOR', NULL, 154),
(1556, 'SONDORILLO', NULL, 154),
(1557, 'CHULUCANAS', NULL, 155),
(1558, 'BUENOS AIRES', NULL, 155),
(1559, 'CHALACO', NULL, 155),
(1560, 'LA MATANZA', NULL, 155),
(1561, 'MORROPON', NULL, 155),
(1562, 'SALITRAL', NULL, 155),
(1563, 'SAN JUAN DE BIGOTE', NULL, 155),
(1564, 'SANTA CATALINA DE MOSSA', NULL, 155),
(1565, 'SANTO DOMINGO', NULL, 155),
(1566, 'YAMANGO', NULL, 155),
(1567, 'PAITA', NULL, 156),
(1568, 'AMOTAPE', NULL, 156),
(1569, 'ARENAL', NULL, 156),
(1570, 'COLAN', NULL, 156),
(1571, 'LA HUACA', NULL, 156),
(1572, 'TAMARINDO', NULL, 156),
(1573, 'VICHAYAL', NULL, 156),
(1574, 'SULLANA', NULL, 157),
(1575, 'BELLAVISTA', NULL, 157),
(1576, 'IGNACIO ESCUDERO', NULL, 157),
(1577, 'LANCONES', NULL, 157),
(1578, 'MARCAVELICA', NULL, 157),
(1579, 'MIGUEL CHECA', NULL, 157),
(1580, 'QUERECOTILLO', NULL, 157),
(1581, 'SALITRAL', NULL, 157),
(1582, 'PARI&Ntilde;AS', NULL, 158),
(1583, 'EL ALTO', NULL, 158),
(1584, 'LA BREA', NULL, 158),
(1585, 'LOBITOS', NULL, 158),
(1586, 'LOS ORGANOS', NULL, 158),
(1587, 'MANCORA', NULL, 158),
(1588, 'SECHURA', NULL, 159),
(1589, 'BELLAVISTA DE LA UNION', NULL, 159),
(1590, 'BERNAL', NULL, 159),
(1591, 'CRISTO NOS VALGA', NULL, 159),
(1592, 'VICE', NULL, 159),
(1593, 'RINCONADA LLICUAR', NULL, 159),
(1594, 'PUNO', NULL, 160),
(1595, 'ACORA', NULL, 160),
(1596, 'AMANTANI', NULL, 160),
(1597, 'ATUNCOLLA', NULL, 160),
(1598, 'CAPACHICA', NULL, 160),
(1599, 'CHUCUITO', NULL, 160),
(1600, 'COATA', NULL, 160),
(1601, 'HUATA', NULL, 160),
(1602, 'MA&Ntilde;AZO', NULL, 160),
(1603, 'PAUCARCOLLA', NULL, 160),
(1604, 'PICHACANI', NULL, 160),
(1605, 'PLATERIA', NULL, 160),
(1606, 'SAN ANTONIO', NULL, 160),
(1607, 'TIQUILLACA', NULL, 160),
(1608, 'VILQUE', NULL, 160),
(1609, 'AZANGARO', NULL, 161),
(1610, 'ACHAYA', NULL, 161),
(1611, 'ARAPA', NULL, 161),
(1612, 'ASILLO', NULL, 161),
(1613, 'CAMINACA', NULL, 161),
(1614, 'CHUPA', NULL, 161),
(1615, 'JOSE DOMINGO CHOQUEHUANCA', NULL, 161),
(1616, 'MU&Ntilde;ANI', NULL, 161),
(1617, 'POTONI', NULL, 161),
(1618, 'SAMAN', NULL, 161),
(1619, 'SAN ANTON', NULL, 161),
(1620, 'SAN JOSE', NULL, 161),
(1621, 'SAN JUAN DE SALINAS', NULL, 161),
(1622, 'SANTIAGO DE PUPUJA', NULL, 161),
(1623, 'TIRAPATA', NULL, 161),
(1624, 'MACUSANI', NULL, 162),
(1625, 'AJOYANI', NULL, 162),
(1626, 'AYAPATA', NULL, 162),
(1627, 'COASA', NULL, 162),
(1628, 'CORANI', NULL, 162),
(1629, 'CRUCERO', NULL, 162),
(1630, 'ITUATA', NULL, 162),
(1631, 'OLLACHEA', NULL, 162),
(1632, 'SAN GABAN', NULL, 162),
(1633, 'USICAYOS', NULL, 162),
(1634, 'JULI', NULL, 163),
(1635, 'DESAGUADERO', NULL, 163),
(1636, 'HUACULLANI', NULL, 163),
(1637, 'KELLUYO', NULL, 163),
(1638, 'PISACOMA', NULL, 163),
(1639, 'POMATA', NULL, 163),
(1640, 'ZEPITA', NULL, 163),
(1641, 'ILAVE', NULL, 164),
(1642, 'CAPAZO', NULL, 164),
(1643, 'PILCUYO', NULL, 164),
(1644, 'SANTA ROSA', NULL, 164),
(1645, 'CONDURIRI', NULL, 164),
(1646, 'HUANCANE', NULL, 165),
(1647, 'COJATA', NULL, 165),
(1648, 'HUATASANI', NULL, 165),
(1649, 'INCHUPALLA', NULL, 165),
(1650, 'PUSI', NULL, 165),
(1651, 'ROSASPATA', NULL, 165),
(1652, 'TARACO', NULL, 165),
(1653, 'VILQUE CHICO', NULL, 165),
(1654, 'LAMPA', NULL, 166),
(1655, 'CABANILLA', NULL, 166),
(1656, 'CALAPUJA', NULL, 166),
(1657, 'NICASIO', NULL, 166),
(1658, 'OCUVIRI', NULL, 166),
(1659, 'PALCA', NULL, 166),
(1660, 'PARATIA', NULL, 166),
(1661, 'PUCARA', NULL, 166),
(1662, 'SANTA LUCIA', NULL, 166),
(1663, 'VILAVILA', NULL, 166),
(1664, 'AYAVIRI', NULL, 167),
(1665, 'ANTAUTA', NULL, 167),
(1666, 'CUPI', NULL, 167),
(1667, 'LLALLI', NULL, 167),
(1668, 'MACARI', NULL, 167),
(1669, 'NU&Ntilde;OA', NULL, 167),
(1670, 'ORURILLO', NULL, 167),
(1671, 'SANTA ROSA', NULL, 167),
(1672, 'UMACHIRI', NULL, 167),
(1673, 'MOHO', NULL, 168),
(1674, 'CONIMA', NULL, 168),
(1675, 'HUAYRAPATA', NULL, 168),
(1676, 'TILALI', NULL, 168),
(1677, 'PUTINA', NULL, 169),
(1678, 'ANANEA', NULL, 169),
(1679, 'PEDRO VILCA APAZA', NULL, 169),
(1680, 'QUILCAPUNCU', NULL, 169),
(1681, 'SINA', NULL, 169),
(1682, 'JULIACA', NULL, 170),
(1683, 'CABANA', NULL, 170),
(1684, 'CABANILLAS', NULL, 170),
(1685, 'CARACOTO', NULL, 170),
(1686, 'SANDIA', NULL, 171),
(1687, 'CUYOCUYO', NULL, 171),
(1688, 'LIMBANI', NULL, 171),
(1689, 'PATAMBUCO', NULL, 171),
(1690, 'PHARA', NULL, 171),
(1691, 'QUIACA', NULL, 171),
(1692, 'SAN JUAN DEL ORO', NULL, 171),
(1693, 'YANAHUAYA', NULL, 171),
(1694, 'ALTO INAMBARI', NULL, 171),
(1695, 'YUNGUYO', NULL, 172),
(1696, 'ANAPIA', NULL, 172),
(1697, 'COPANI', NULL, 172),
(1698, 'CUTURAPI', NULL, 172),
(1699, 'OLLARAYA', NULL, 172),
(1700, 'TINICACHI', NULL, 172),
(1701, 'UNICACHI', NULL, 172),
(1702, 'MOYOBAMBA', NULL, 173),
(1703, 'CALZADA', NULL, 173),
(1704, 'HABANA', NULL, 173),
(1705, 'JEPELACIO', NULL, 173),
(1706, 'SORITOR', NULL, 173),
(1707, 'YANTALO', NULL, 173),
(1708, 'BELLAVISTA', NULL, 174),
(1709, 'ALTO BIAVO', NULL, 174),
(1710, 'BAJO BIAVO', NULL, 174),
(1711, 'HUALLAGA', NULL, 174),
(1712, 'SAN PABLO', NULL, 174),
(1713, 'SAN RAFAEL', NULL, 174),
(1714, 'SAN JOSE DE SISA', NULL, 175),
(1715, 'AGUA BLANCA', NULL, 175),
(1716, 'SAN MARTIN', NULL, 175),
(1717, 'SANTA ROSA', NULL, 175),
(1718, 'SHATOJA', NULL, 175),
(1719, 'SAPOSOA', NULL, 176);
INSERT INTO `cnf_distrito` (`cnf_distrito_id`, `nombre`, `value`, `cnf_provincia_id`) VALUES
(1720, 'ALTO SAPOSOA', NULL, 176),
(1721, 'EL ESLABON', NULL, 176),
(1722, 'PISCOYACU', NULL, 176),
(1723, 'SACANCHE', NULL, 176),
(1724, 'TINGO DE SAPOSOA', NULL, 176),
(1725, 'LAMAS', NULL, 177),
(1726, 'ALONSO DE ALVARADO', NULL, 177),
(1727, 'BARRANQUITA', NULL, 177),
(1728, 'CAYNARACHI', NULL, 177),
(1729, 'CU&Ntilde;UMBUQUI', NULL, 177),
(1730, 'PINTO RECODO', NULL, 177),
(1731, 'RUMISAPA', NULL, 177),
(1732, 'SAN ROQUE DE CUMBAZA', NULL, 177),
(1733, 'SHANAO', NULL, 177),
(1734, 'TABALOSOS', NULL, 177),
(1735, 'ZAPATERO', NULL, 177),
(1736, 'JUANJUI', NULL, 178),
(1737, 'CAMPANILLA', NULL, 178),
(1738, 'HUICUNGO', NULL, 178),
(1739, 'PACHIZA', NULL, 178),
(1740, 'PAJARILLO', NULL, 178),
(1741, 'PICOTA', NULL, 179),
(1742, 'BUENOS AIRES', NULL, 179),
(1743, 'CASPISAPA', NULL, 179),
(1744, 'PILLUANA', NULL, 179),
(1745, 'PUCACACA', NULL, 179),
(1746, 'SAN CRISTOBAL', NULL, 179),
(1747, 'SAN HILARION', NULL, 179),
(1748, 'SHAMBOYACU', NULL, 179),
(1749, 'TINGO DE PONASA', NULL, 179),
(1750, 'TRES UNIDOS', NULL, 179),
(1751, 'RIOJA', NULL, 180),
(1752, 'AWAJUN', NULL, 180),
(1753, 'ELIAS SOPLIN VARGAS', NULL, 180),
(1754, 'NUEVA CAJAMARCA', NULL, 180),
(1755, 'PARDO MIGUEL', NULL, 180),
(1756, 'POSIC', NULL, 180),
(1757, 'SAN FERNANDO', NULL, 180),
(1758, 'YORONGOS', NULL, 180),
(1759, 'YURACYACU', NULL, 180),
(1760, 'TARAPOTO', NULL, 181),
(1761, 'ALBERTO LEVEAU', NULL, 181),
(1762, 'CACATACHI', NULL, 181),
(1763, 'CHAZUTA', NULL, 181),
(1764, 'CHIPURANA', NULL, 181),
(1765, 'EL PORVENIR', NULL, 181),
(1766, 'HUIMBAYOC', NULL, 181),
(1767, 'JUAN GUERRA', NULL, 181),
(1768, 'LA BANDA DE SHILCAYO', NULL, 181),
(1769, 'MORALES', NULL, 181),
(1770, 'PAPAPLAYA', NULL, 181),
(1771, 'SAN ANTONIO', NULL, 181),
(1772, 'SAUCE', NULL, 181),
(1773, 'SHAPAJA', NULL, 181),
(1774, 'TOCACHE', NULL, 182),
(1775, 'NUEVO PROGRESO', NULL, 182),
(1776, 'POLVORA', NULL, 182),
(1777, 'SHUNTE', NULL, 182),
(1778, 'UCHIZA', NULL, 182),
(1779, 'TACNA', NULL, 183),
(1780, 'ALTO DE LA ALIANZA', NULL, 183),
(1781, 'CALANA', NULL, 183),
(1782, 'CIUDAD NUEVA', NULL, 183),
(1783, 'INCLAN', NULL, 183),
(1784, 'PACHIA', NULL, 183),
(1785, 'PALCA', NULL, 183),
(1786, 'POCOLLAY', NULL, 183),
(1787, 'SAMA', NULL, 183),
(1788, 'CORONEL GREGORIO ALBARRACIN LANCHIPA', NULL, 183),
(1789, 'CANDARAVE', NULL, 184),
(1790, 'CAIRANI', NULL, 184),
(1791, 'CAMILACA', NULL, 184),
(1792, 'CURIBAYA', NULL, 184),
(1793, 'HUANUARA', NULL, 184),
(1794, 'QUILAHUANI', NULL, 184),
(1795, 'LOCUMBA', NULL, 185),
(1796, 'ILABAYA', NULL, 185),
(1797, 'ITE', NULL, 185),
(1798, 'TARATA', NULL, 186),
(1799, 'CHUCATAMANI', NULL, 186),
(1800, 'ESTIQUE', NULL, 186),
(1801, 'ESTIQUE-PAMPA', NULL, 186),
(1802, 'SITAJARA', NULL, 186),
(1803, 'SUSAPAYA', NULL, 186),
(1804, 'TARUCACHI', NULL, 186),
(1805, 'TICACO', NULL, 186),
(1806, 'TUMBES', NULL, 187),
(1807, 'CORRALES', NULL, 187),
(1808, 'LA CRUZ', NULL, 187),
(1809, 'PAMPAS DE HOSPITAL', NULL, 187),
(1810, 'SAN JACINTO', NULL, 187),
(1811, 'SAN JUAN DE LA VIRGEN', NULL, 187),
(1812, 'ZORRITOS', NULL, 188),
(1813, 'CASITAS', NULL, 188),
(1814, 'ZARUMILLA', NULL, 189),
(1815, 'AGUAS VERDES', NULL, 189),
(1816, 'MATAPALO', NULL, 189),
(1817, 'PAPAYAL', NULL, 189),
(1818, 'CALLERIA', NULL, 190),
(1819, 'CAMPOVERDE', NULL, 190),
(1820, 'IPARIA', NULL, 190),
(1821, 'MASISEA', NULL, 190),
(1822, 'YARINACOCHA', NULL, 190),
(1823, 'NUEVA REQUENA', NULL, 190),
(1824, 'RAYMONDI', NULL, 191),
(1825, 'SEPAHUA', NULL, 191),
(1826, 'TAHUANIA', NULL, 191),
(1827, 'YURUA', NULL, 191),
(1828, 'PADRE ABAD', NULL, 192),
(1829, 'IRAZOLA', NULL, 192),
(1830, 'CURIMANA', NULL, 192),
(1831, 'PURUS', NULL, 193);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_empresa`
--

CREATE TABLE `cnf_empresa` (
  `cnf_empresa_id` bigint(20) NOT NULL,
  `descripcion` varchar(300) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `empresacol` varchar(45) DEFAULT NULL,
  `estado` varchar(1) DEFAULT NULL,
  `nombre` varchar(200) NOT NULL,
  `nro_documento` varchar(11) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `token` varchar(1000) DEFAULT NULL,
  `cnf_distrito_id` bigint(20) DEFAULT NULL,
  `cnf_tipo_documento_id` bigint(20) NOT NULL,
  `ruta_pse` varchar(1000) DEFAULT NULL,
  `perfil_empresa` int(11) DEFAULT NULL,
  `flag_compra_rapida` int(11) NOT NULL DEFAULT '0',
  `flag_venta_rapida` int(11) NOT NULL DEFAULT '0',
  `cnf_moneda_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_empresa`
--

INSERT INTO `cnf_empresa` (`cnf_empresa_id`, `descripcion`, `direccion`, `empresacol`, `estado`, `nombre`, `nro_documento`, `telefono`, `token`, `cnf_distrito_id`, `cnf_tipo_documento_id`, `ruta_pse`, `perfil_empresa`, `flag_compra_rapida`, `flag_venta_rapida`, `cnf_moneda_id`) VALUES
(1, 'DEINSOFT', 'JR LOS CLAVELES 123', NULL, '1', 'DESARROLLO INTEGRAL DE SOFTWARE', '20534999616', '1322323', '', 1004, 3, '', NULL, 0, 0, 1),
(2, 'BATERIAS DON TELE', 'AV PANAMERICANA SUR 1332 - A', NULL, '1', 'BATERIAS DON TELE', '12345678901', '', 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NTA5MjAwMTYsImlzcyI6IkRFSU5TT0ZUIiwianRpIjoiREVGQUNULUpXVCIsInN1YiI6IjEwNDE0MzE2NTk1L1BFUkVaIERFTEdBRE8gQkxBTkNBIE5FUkkiLCJudW1Eb2MiOiIxMDQxNDMxNjU5NSIsInJhem9uU29jaWFsIjoiUEVSRVogREVMR0FETyBCTEFOQ0EgTkVSSSIsInVzdWF', 1004, 3, 'http://localhost:8080/api/v1/document/send-document', NULL, 0, 0, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_forma_pago`
--

CREATE TABLE `cnf_forma_pago` (
  `cnf_forma_pago_id` bigint(20) NOT NULL,
  `flag_estado` varchar(1) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `tipo` int(11) DEFAULT NULL,
  `cnf_empresa_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_forma_pago`
--

INSERT INTO `cnf_forma_pago` (`cnf_forma_pago_id`, `flag_estado`, `nombre`, `tipo`, `cnf_empresa_id`) VALUES
(1, NULL, 'Contado', NULL, 1),
(2, NULL, 'Credito a 60 días', 1, 1),
(7, NULL, 'CONTADO', 1, 2),
(8, NULL, 'CREDITO', 1, 2),
(9, NULL, 'FACTURA A 60 DIAS', 1, 2),
(10, NULL, 'MENSUAL', 2, 2),
(11, NULL, 'MENSUAL', 2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_forma_pago_detalle`
--

CREATE TABLE `cnf_forma_pago_detalle` (
  `cnf_forma_pago_detalle_id` bigint(20) NOT NULL,
  `modo_dia_vencimiento` int(11) DEFAULT NULL,
  `modo_dias_intervalo` int(11) DEFAULT NULL,
  `modo_monto` decimal(19,2) DEFAULT NULL,
  `modo_porcentaje` decimal(19,2) DEFAULT NULL,
  `cnf_forma_pago_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_forma_pago_detalle`
--

INSERT INTO `cnf_forma_pago_detalle` (`cnf_forma_pago_detalle_id`, `modo_dia_vencimiento`, `modo_dias_intervalo`, `modo_monto`, `modo_porcentaje`, `cnf_forma_pago_id`) VALUES
(2, NULL, 30, NULL, '50.00', 9),
(3, NULL, 60, NULL, '50.00', 9),
(4, 31, NULL, '100.00', NULL, 10),
(6, 31, NULL, '50.00', NULL, 11),
(7, 31, NULL, NULL, NULL, 10),
(8, NULL, 30, NULL, '50.00', 2),
(9, NULL, NULL, NULL, NULL, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_impuesto_condicion`
--

CREATE TABLE `cnf_impuesto_condicion` (
  `cnf_impuesto_condicion_id` bigint(20) NOT NULL,
  `codigo_sunat` varchar(2) DEFAULT NULL,
  `nombre` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_impuesto_condicion`
--

INSERT INTO `cnf_impuesto_condicion` (`cnf_impuesto_condicion_id`, `codigo_sunat`, `nombre`) VALUES
(1, '10', 'Gravado - Operación Onerosa');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_local`
--

CREATE TABLE `cnf_local` (
  `cnf_local_id` bigint(20) NOT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `cnf_empresa_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_local`
--

INSERT INTO `cnf_local` (`cnf_local_id`, `direccion`, `nombre`, `cnf_empresa_id`) VALUES
(1, NULL, 'LOCAL PRINCIPAL', 1),
(2, NULL, 'LOCAL PRINCIPAL', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_maestro`
--

CREATE TABLE `cnf_maestro` (
  `cnf_maestro_id` bigint(20) NOT NULL,
  `apellido_materno` varchar(100) DEFAULT NULL,
  `apellido_paterno` varchar(100) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `fecha_registro` datetime(6) DEFAULT NULL,
  `flag_estado` varchar(1) DEFAULT NULL,
  `nombres` varchar(100) DEFAULT NULL,
  `nro_doc` varchar(11) DEFAULT NULL,
  `razon_social` varchar(100) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `cnf_distrito_id` bigint(20) DEFAULT NULL,
  `cnf_empresa_id` bigint(20) DEFAULT NULL,
  `cnf_tipo_documento_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_maestro`
--

INSERT INTO `cnf_maestro` (`cnf_maestro_id`, `apellido_materno`, `apellido_paterno`, `correo`, `direccion`, `fecha_registro`, `flag_estado`, `nombres`, `nro_doc`, `razon_social`, `telefono`, `cnf_distrito_id`, `cnf_empresa_id`, `cnf_tipo_documento_id`) VALUES
(1, '', '', '', '', NULL, '1', 'Edward ', '', '', '', 23, 1, 2),
(2, '', '', '', '', NULL, '1', 'paul', '12222222', '', '', NULL, 1, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_marca`
--

CREATE TABLE `cnf_marca` (
  `cnf_marca_id` bigint(20) NOT NULL,
  `flag_estado` varchar(1) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `cnf_empresa_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_marca`
--

INSERT INTO `cnf_marca` (`cnf_marca_id`, `flag_estado`, `nombre`, `cnf_empresa_id`) VALUES
(1, NULL, 'ETNA', 1),
(2, NULL, 'Pokemon Trading Card Game', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_moneda`
--

CREATE TABLE `cnf_moneda` (
  `cnf_moneda_id` bigint(20) NOT NULL,
  `codigo` varchar(3) DEFAULT NULL,
  `flag_estado` varchar(1) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `descripcion_plural` varchar(500) DEFAULT NULL,
  `simbolo` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_moneda`
--

INSERT INTO `cnf_moneda` (`cnf_moneda_id`, `codigo`, `flag_estado`, `nombre`, `descripcion_plural`, `simbolo`) VALUES
(1, 'PEN', NULL, 'SOLES', 'SOLES', 'S/'),
(2, 'USD', NULL, 'DOLAR', 'DOLARES', '$USD');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_num_comprobante`
--

CREATE TABLE `cnf_num_comprobante` (
  `cnf_num_comprobante_id` bigint(20) NOT NULL,
  `serie` varchar(4) DEFAULT NULL,
  `ultimo_nro` int(11) NOT NULL,
  `cnf_local_id` bigint(20) NOT NULL,
  `cnf_tipo_comprobante_id` bigint(20) NOT NULL,
  `cnf_empresa_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_num_comprobante`
--

INSERT INTO `cnf_num_comprobante` (`cnf_num_comprobante_id`, `serie`, `ultimo_nro`, `cnf_local_id`, `cnf_tipo_comprobante_id`, `cnf_empresa_id`) VALUES
(1, 'BB01', 1, 1, 1, 1),
(2, 'FF01', 0, 1, 2, 1),
(3, '0001', 1, 1, 4, 1),
(4, 'CN01', 0, 1, 7, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_pais`
--

CREATE TABLE `cnf_pais` (
  `cnf_pais_id` bigint(20) NOT NULL,
  `isocode` varchar(2) NOT NULL,
  `nombre` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_pais`
--

INSERT INTO `cnf_pais` (`cnf_pais_id`, `isocode`, `nombre`) VALUES
(1, 'PE', 'PERÚ');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_plan_contrato`
--

CREATE TABLE `cnf_plan_contrato` (
  `cnf_plan_contrato_id` bigint(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio` decimal(19,2) NOT NULL,
  `cnf_empresa_id` bigint(20) DEFAULT NULL,
  `dia_vencimiento` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_plan_contrato`
--

INSERT INTO `cnf_plan_contrato` (`cnf_plan_contrato_id`, `nombre`, `precio`, `cnf_empresa_id`, `dia_vencimiento`) VALUES
(1, 'TV CABLE 60 CANALES', '79.00', 1, 31),
(2, 'TV CABLE 60 CANALES + INTERNET', '129.00', 1, 31);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_producto`
--

CREATE TABLE `cnf_producto` (
  `cnf_producto_id` bigint(20) NOT NULL,
  `barcode` varchar(100) DEFAULT NULL,
  `codigo` varchar(15) DEFAULT NULL,
  `costo` decimal(19,2) DEFAULT NULL,
  `existencia` decimal(19,2) DEFAULT NULL,
  `fecha_registro` datetime(6) DEFAULT NULL,
  `flag_estado` varchar(1) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `precio` decimal(19,2) DEFAULT NULL,
  `ruta_imagen` varchar(100) DEFAULT NULL,
  `cnf_categoria_id` bigint(20) DEFAULT NULL,
  `cnf_empresa_id` bigint(20) DEFAULT NULL,
  `cnf_marca_id` bigint(20) DEFAULT NULL,
  `cnf_sub_categoria_id` bigint(20) NOT NULL,
  `cnf_unidad_medida_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_producto`
--

INSERT INTO `cnf_producto` (`cnf_producto_id`, `barcode`, `codigo`, `costo`, `existencia`, `fecha_registro`, `flag_estado`, `nombre`, `precio`, `ruta_imagen`, `cnf_categoria_id`, `cnf_empresa_id`, `cnf_marca_id`, `cnf_sub_categoria_id`, `cnf_unidad_medida_id`) VALUES
(1, '', '', '300.00', NULL, '2023-02-25 06:32:44.900000', '1', 'BATERIA ETNA BGH 1232', '350.00', 'https://deinsoft-cloud.azurewebsites.net/ventas-backend/resources/20230225063244.jpg', NULL, 1, 1, 1, 1),
(2, '', '0001', NULL, NULL, '2023-02-24 02:35:31.568000', '1', 'Pikachu VMax', NULL, '', NULL, 1, 2, 8, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_provincia`
--

CREATE TABLE `cnf_provincia` (
  `cnf_provincia_id` bigint(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `cnf_region_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_provincia`
--

INSERT INTO `cnf_provincia` (`cnf_provincia_id`, `nombre`, `cnf_region_id`) VALUES
(1, 'CHACHAPOYAS ', 1),
(2, 'BAGUA', 1),
(3, 'BONGARA', 1),
(4, 'CONDORCANQUI', 1),
(5, 'LUYA', 1),
(6, 'RODRIGUEZ DE MENDOZA', 1),
(7, 'UTCUBAMBA', 1),
(8, 'HUARAZ', 2),
(9, 'AIJA', 2),
(10, 'ANTONIO RAYMONDI', 2),
(11, 'ASUNCION', 2),
(12, 'BOLOGNESI', 2),
(13, 'CARHUAZ', 2),
(14, 'CARLOS FERMIN FITZCARRALD', 2),
(15, 'CASMA', 2),
(16, 'CORONGO', 2),
(17, 'HUARI', 2),
(18, 'HUARMEY', 2),
(19, 'HUAYLAS', 2),
(20, 'MARISCAL LUZURIAGA', 2),
(21, 'OCROS', 2),
(22, 'PALLASCA', 2),
(23, 'POMABAMBA', 2),
(24, 'RECUAY', 2),
(25, 'SANTA', 2),
(26, 'SIHUAS', 2),
(27, 'YUNGAY', 2),
(28, 'ABANCAY', 3),
(29, 'ANDAHUAYLAS', 3),
(30, 'ANTABAMBA', 3),
(31, 'AYMARAES', 3),
(32, 'COTABAMBAS', 3),
(33, 'CHINCHEROS', 3),
(34, 'GRAU', 3),
(35, 'AREQUIPA', 4),
(36, 'CAMANA', 4),
(37, 'CARAVELI', 4),
(38, 'CASTILLA', 4),
(39, 'CAYLLOMA', 4),
(40, 'CONDESUYOS', 4),
(41, 'ISLAY', 4),
(42, 'LA UNION', 4),
(43, 'HUAMANGA', 5),
(44, 'CANGALLO', 5),
(45, 'HUANCA SANCOS', 5),
(46, 'HUANTA', 5),
(47, 'LA MAR', 5),
(48, 'LUCANAS', 5),
(49, 'PARINACOCHAS', 5),
(50, 'PAUCAR DEL SARA SARA', 5),
(51, 'SUCRE', 5),
(52, 'VICTOR FAJARDO', 5),
(53, 'VILCAS HUAMAN', 5),
(54, 'CAJAMARCA', 6),
(55, 'CAJABAMBA', 6),
(56, 'CELENDIN', 6),
(57, 'CHOTA ', 6),
(58, 'CONTUMAZA', 6),
(59, 'CUTERVO', 6),
(60, 'HUALGAYOC', 6),
(61, 'JAEN', 6),
(62, 'SAN IGNACIO', 6),
(63, 'SAN MARCOS', 6),
(64, 'SAN PABLO', 6),
(65, 'SANTA CRUZ', 6),
(66, 'CALLAO', 7),
(67, 'CUSCO', 8),
(68, 'ACOMAYO', 8),
(69, 'ANTA', 8),
(70, 'CALCA', 8),
(71, 'CANAS', 8),
(72, 'CANCHIS', 8),
(73, 'CHUMBIVILCAS', 8),
(74, 'ESPINAR', 8),
(75, 'LA CONVENCION', 8),
(76, 'PARURO', 8),
(77, 'PAUCARTAMBO', 8),
(78, 'QUISPICANCHI', 8),
(79, 'URUBAMBA', 8),
(80, 'HUANCAVELICA', 9),
(81, 'ACOBAMBA', 9),
(82, 'ANGARAES', 9),
(83, 'CASTROVIRREYNA', 9),
(84, 'CHURCAMPA', 9),
(85, 'HUAYTARA', 9),
(86, 'TAYACAJA', 9),
(87, 'HUANUCO', 10),
(88, 'AMBO', 10),
(89, 'DOS DE MAYO', 10),
(90, 'HUACAYBAMBA', 10),
(91, 'HUAMALIES', 10),
(92, 'LEONCIO PRADO', 10),
(93, 'MARA&Ntilde;ON', 10),
(94, 'PACHITEA', 10),
(95, 'PUERTO INCA', 10),
(96, 'LAURICOCHA', 10),
(97, 'YAROWILCA', 10),
(98, 'ICA', 11),
(99, 'CHINCHA', 11),
(100, 'NAZCA', 11),
(101, 'PALPA', 11),
(102, 'PISCO', 11),
(103, 'HUANCAYO', 12),
(104, 'CONCEPCION', 12),
(105, 'CHANCHAMAYO', 12),
(106, 'JAUJA', 12),
(107, 'JUNIN', 12),
(108, 'SATIPO', 12),
(109, 'TARMA', 12),
(110, 'YAULI', 12),
(111, 'CHUPACA', 12),
(112, 'TRUJILLO', 13),
(113, 'ASCOPE', 13),
(114, 'BOLIVAR', 13),
(115, 'CHEPEN', 13),
(116, 'JULCAN', 13),
(117, 'OTUZCO', 13),
(118, 'PACASMAYO', 13),
(119, 'PATAZ', 13),
(120, 'SANCHEZ CARRION', 13),
(121, 'SANTIAGO DE CHUCO', 13),
(122, 'GRAN CHIMU', 13),
(123, 'VIRU', 13),
(124, 'CHICLAYO', 14),
(125, 'FERRE&Ntilde;AFE', 14),
(126, 'LAMBAYEQUE', 14),
(127, 'LIMA', 15),
(128, 'BARRANCA', 15),
(129, 'CAJATAMBO', 15),
(130, 'CANTA', 15),
(131, 'CA&Ntilde;ETE', 15),
(132, 'HUARAL', 15),
(133, 'HUAROCHIRI', 15),
(134, 'HUAURA', 15),
(135, 'OYON', 15),
(136, 'YAUYOS', 15),
(137, 'MAYNAS', 16),
(138, 'ALTO AMAZONAS', 16),
(139, 'LORETO', 16),
(140, 'MARISCAL RAMON CASTILLA', 16),
(141, 'REQUENA', 16),
(142, 'UCAYALI', 16),
(143, 'TAMBOPATA', 17),
(144, 'MANU', 17),
(145, 'TAHUAMANU', 17),
(146, 'MARISCAL NIETO', 18),
(147, 'GENERAL SANCHEZ CERRO', 18),
(148, 'ILO', 18),
(149, 'PASCO', 19),
(150, 'DANIEL ALCIDES CARRION', 19),
(151, 'OXAPAMPA', 19),
(152, 'PIURA', 20),
(153, 'AYABACA', 20),
(154, 'HUANCABAMBA', 20),
(155, 'MORROPON', 20),
(156, 'PAITA', 20),
(157, 'SULLANA', 20),
(158, 'TALARA', 20),
(159, 'SECHURA', 20),
(160, 'PUNO', 21),
(161, 'AZANGARO', 21),
(162, 'CARABAYA', 21),
(163, 'CHUCUITO', 21),
(164, 'EL COLLAO', 21),
(165, 'HUANCANE', 21),
(166, 'LAMPA', 21),
(167, 'MELGAR', 21),
(168, 'MOHO', 21),
(169, 'SAN ANTONIO DE PUTINA', 21),
(170, 'SAN ROMAN', 21),
(171, 'SANDIA', 21),
(172, 'YUNGUYO', 21),
(173, 'MOYOBAMBA', 22),
(174, 'BELLAVISTA', 22),
(175, 'EL DORADO', 22),
(176, 'HUALLAGA', 22),
(177, 'LAMAS', 22),
(178, 'MARISCAL CACERES', 22),
(179, 'PICOTA', 22),
(180, 'RIOJA', 22),
(181, 'SAN MARTIN', 22),
(182, 'TOCACHE', 22),
(183, 'TACNA', 23),
(184, 'CANDARAVE', 23),
(185, 'JORGE BASADRE', 23),
(186, 'TARATA', 23),
(187, 'TUMBES', 24),
(188, 'CONTRALMIRANTE VILLAR', 24),
(189, 'ZARUMILLA', 24),
(190, 'CORONEL PORTILLO', 25),
(191, 'ATALAYA', 25),
(192, 'PADRE ABAD', 25),
(193, 'PURUS', 25);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_region`
--

CREATE TABLE `cnf_region` (
  `cnf_region_id` bigint(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `cnf_pais_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_region`
--

INSERT INTO `cnf_region` (`cnf_region_id`, `nombre`, `cnf_pais_id`) VALUES
(1, 'AMAZONAS', 1),
(2, 'ANCASH', 1),
(3, 'APURIMAC', 1),
(4, 'AREQUIPA', 1),
(5, 'AYACUCHO', 1),
(6, 'CAJAMARCA', 1),
(7, 'CALLAO', 1),
(8, 'CUSCO', 1),
(9, 'HUANCAVELICA', 1),
(10, 'HUANUCO', 1),
(11, 'ICA', 1),
(12, 'JUNIN', 1),
(13, 'LA LIBERTAD', 1),
(14, 'LAMBAYEQUE', 1),
(15, 'LIMA', 1),
(16, 'LORETO', 1),
(17, 'MADRE DE DIOS', 1),
(18, 'MOQUEGUA', 1),
(19, 'PASCO', 1),
(20, 'PIURA', 1),
(21, 'PUNO', 1),
(22, 'SAN MARTIN', 1),
(23, 'TACNA', 1),
(24, 'TUMBES', 1),
(25, 'UCAYALI', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_sub_categoria`
--

CREATE TABLE `cnf_sub_categoria` (
  `cnf_sub_categoria_id` bigint(20) NOT NULL,
  `flag_estado` varchar(1) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `cnf_categoria_id` bigint(20) NOT NULL,
  `cnf_empresa_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_sub_categoria`
--

INSERT INTO `cnf_sub_categoria` (`cnf_sub_categoria_id`, `flag_estado`, `nombre`, `cnf_categoria_id`, `cnf_empresa_id`) VALUES
(1, NULL, 'BATERIAS AUTO', 1, 1),
(6, NULL, 'SilverTempest', 4, 1),
(7, NULL, 'Lost Origin', 4, 1),
(8, NULL, 'Crown Zenith', 4, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_tipo_comprobante`
--

CREATE TABLE `cnf_tipo_comprobante` (
  `cnf_tipo_comprobante_id` bigint(20) NOT NULL,
  `codigo` varchar(3) DEFAULT NULL,
  `codigo_sunat` varchar(2) DEFAULT NULL,
  `flag_electronico` varchar(1) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_tipo_comprobante`
--

INSERT INTO `cnf_tipo_comprobante` (`cnf_tipo_comprobante_id`, `codigo`, `codigo_sunat`, `flag_electronico`, `nombre`) VALUES
(1, 'BOL', '03', '1', 'BOLETA'),
(2, 'FAC', '01', '1', 'FACTURA'),
(4, 'NOT', '00', '0', 'NOTA DE VENTA'),
(5, 'NSA', '', '0', 'NOTA DE SALIDA'),
(6, 'NEA', '', '0', 'NOTA DE ENTRADA'),
(7, '', '', '0', 'CONTRATO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_tipo_documento`
--

CREATE TABLE `cnf_tipo_documento` (
  `cnf_tipo_documento_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `abreviatura` varchar(3) DEFAULT NULL,
  `codigo_sunat` varchar(2) DEFAULT NULL,
  `flag_estado` varchar(1) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_tipo_documento`
--

INSERT INTO `cnf_tipo_documento` (`cnf_tipo_documento_id`, `name`, `value`, `abreviatura`, `codigo_sunat`, `flag_estado`, `nombre`) VALUES
(2, '', NULL, 'DNI', '1', NULL, 'DOCUMENTO NACIONAL DE IDENTIDAD'),
(3, '', NULL, 'RUC', '6', NULL, 'REGISTRO ÚNICO DE CONTRIBUYENTE');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_unidad_medida`
--

CREATE TABLE `cnf_unidad_medida` (
  `cnf_unidad_medida_id` bigint(20) NOT NULL,
  `codigo_sunat` varchar(3) DEFAULT NULL,
  `flag_estado` varchar(1) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_unidad_medida`
--

INSERT INTO `cnf_unidad_medida` (`cnf_unidad_medida_id`, `codigo_sunat`, `flag_estado`, `nombre`) VALUES
(1, 'NIU', '1', 'UNIDAD (BIENES)'),
(3, '4A', '1', 'BOBINAS'),
(4, 'BJ', '1', 'BALDE'),
(5, 'BLL', '1', 'BARRILES'),
(6, 'BG', '1', 'BOLSA'),
(7, 'BO', '1', 'BOTELLAS'),
(8, 'BX', '1', 'CAJA'),
(9, 'CT', '1', 'CARTONES'),
(10, 'CMK', '1', 'CENTIMETRO CUADRADO'),
(11, 'CMQ', '1', 'CENTIMETRO CUBICO'),
(12, 'CMT', '1', 'CENTIMETRO LINEAL'),
(13, 'CEN', '1', 'CIENTO DE UNIDADES'),
(14, 'CY', '1', 'CILINDRO'),
(15, 'CJ', '1', 'CONOS'),
(16, 'DZN', '1', 'DOCENA'),
(17, 'DZP', '1', 'DOCENA POR 10**6'),
(18, 'BE', '1', 'FARDO'),
(19, 'GLI', '1', 'GALON INGLES (4,545956L)'),
(20, 'GRM', '1', 'GRAMO'),
(21, 'GRO', '1', 'GRUESA'),
(22, 'HLT', '1', 'HECTOLITRO'),
(23, 'LEF', '1', 'HOJA'),
(24, 'SET', '1', 'JUEGO'),
(25, 'KGM', '1', 'KILOGRAMO'),
(26, 'KTM', '1', 'KILOMETRO'),
(27, 'KWH', '1', 'KILOVATIO HORA'),
(28, 'KT', '1', 'KIT'),
(29, 'CA', '1', 'LATAS'),
(30, 'LBR', '1', 'LIBRAS'),
(31, 'LTR', '1', 'LITRO'),
(32, 'MWH', '1', 'MEGAWATT HORA'),
(33, 'MTR', '1', 'METRO'),
(34, 'MTK', '1', 'METRO CUADRADO'),
(35, 'MTQ', '1', 'METRO CUBICO'),
(36, 'MGM', '1', 'MILIGRAMOS'),
(37, 'MLT', '1', 'MILILITRO'),
(38, 'MMT', '1', 'MILIMETRO'),
(39, 'MMK', '1', 'MILIMETRO CUADRADO'),
(40, 'MMQ', '1', 'MILIMETRO CUBICO'),
(41, 'MLL', '1', 'MILLARES'),
(42, 'UM', '1', 'MILLON DE UNIDADES'),
(43, 'ONZ', '1', 'ONZAS'),
(44, 'PF', '1', 'PALETAS'),
(45, 'PK', '1', 'PAQUETE'),
(46, 'PR', '1', 'PAR'),
(47, 'FOT', '1', 'PIES'),
(48, 'FTK', '1', 'PIES CUADRADOS'),
(49, 'FTQ', '1', 'PIES CUBICOS'),
(50, 'C62', '1', 'PIEZAS'),
(51, 'PG', '1', 'PLACAS'),
(52, 'ST', '1', 'PLIEGO'),
(53, 'INH', '1', 'PULGADAS'),
(54, 'RM', '1', 'RESMA'),
(55, 'DR', '1', 'TAMBOR'),
(56, 'STN', '1', 'TONELADA CORTA'),
(57, 'LTN', '1', 'TONELADA LARGA'),
(58, 'TNE', '1', 'TONELADAS'),
(59, 'TU', '1', 'TUBOS'),
(61, 'ZZ', '1', 'UNIDAD (SERVICIOS)'),
(62, 'GLL', '1', 'US GALON (3,7843 L)'),
(63, 'YRD', '1', 'YARDA'),
(64, 'YDK', '1', 'YARDA CUADRADA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cnf_zona`
--

CREATE TABLE `cnf_zona` (
  `cnf_zona_id` bigint(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `cnf_empresa_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cnf_zona`
--

INSERT INTO `cnf_zona` (`cnf_zona_id`, `nombre`, `cnf_empresa_id`) VALUES
(1, 'Sector 5', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inv_almacen`
--

CREATE TABLE `inv_almacen` (
  `inv_almacen_id` bigint(20) NOT NULL,
  `flag_estado` varchar(1) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `cnf_local_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `inv_almacen`
--

INSERT INTO `inv_almacen` (`inv_almacen_id`, `flag_estado`, `nombre`, `cnf_local_id`) VALUES
(1, NULL, 'Almacen principal', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inv_almacen_producto`
--

CREATE TABLE `inv_almacen_producto` (
  `inv_movimiento_producto_id` bigint(20) NOT NULL,
  `cantidad` decimal(19,2) DEFAULT NULL,
  `cnf_producto_id` bigint(20) NOT NULL,
  `inv_almacen_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `inv_almacen_producto`
--

INSERT INTO `inv_almacen_producto` (`inv_movimiento_producto_id`, `cantidad`, `cnf_producto_id`, `inv_almacen_id`) VALUES
(1, '10.00', 1, 1),
(2, '9.00', 2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inv_movimiento_producto`
--

CREATE TABLE `inv_movimiento_producto` (
  `inv_movimiento_producto_id` bigint(20) NOT NULL,
  `cantidad` decimal(19,2) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `fecha_registro` datetime(6) DEFAULT NULL,
  `valor` decimal(19,2) DEFAULT NULL,
  `act_comprobante_id` bigint(20) DEFAULT NULL,
  `cnf_producto_id` bigint(20) DEFAULT NULL,
  `inv_almacen_id` bigint(20) DEFAULT NULL,
  `inv_mov_almacen_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `inv_movimiento_producto`
--

INSERT INTO `inv_movimiento_producto` (`inv_movimiento_producto_id`, `cantidad`, `fecha`, `fecha_registro`, `valor`, `act_comprobante_id`, `cnf_producto_id`, `inv_almacen_id`, `inv_mov_almacen_id`) VALUES
(1, '10.00', '2023-02-21', '2023-02-22 02:55:47.858000', '350.00', 1, 1, 1, NULL),
(2, '10.00', '2023-02-23', '2023-02-24 02:42:38.079000', '80.00', 4, 2, 1, NULL),
(3, '-1.00', '2023-02-23', '2023-02-24 02:43:18.167000', '100.00', 5, 2, 1, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inv_mov_almacen`
--

CREATE TABLE `inv_mov_almacen` (
  `inv_mov_almacen_id` bigint(20) NOT NULL,
  `fecha` date DEFAULT NULL,
  `fechareg` datetime(6) NOT NULL,
  `flag_estado` varchar(1) DEFAULT NULL,
  `igv` decimal(19,2) DEFAULT NULL,
  `numero` varchar(8) DEFAULT NULL,
  `numero_ref` varchar(18) DEFAULT NULL,
  `observacion` varchar(300) DEFAULT NULL,
  `serie` varchar(4) DEFAULT NULL,
  `subtotal` decimal(19,2) DEFAULT NULL,
  `total` decimal(19,2) DEFAULT NULL,
  `cnf_local_id` bigint(20) NOT NULL,
  `cnf_maestro_id` bigint(20) DEFAULT NULL,
  `cnf_tipo_comprobante_id` bigint(20) DEFAULT NULL,
  `inv_almacen_id` bigint(20) DEFAULT NULL,
  `inv_tipo_mov_almacen_id` bigint(20) DEFAULT NULL,
  `seg_usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inv_mov_almacen_det`
--

CREATE TABLE `inv_mov_almacen_det` (
  `inv_mov_almacen_det_id` bigint(20) NOT NULL,
  `cantidad` decimal(19,2) DEFAULT NULL,
  `importe` decimal(19,2) DEFAULT NULL,
  `nroserie` varchar(30) DEFAULT NULL,
  `precio` decimal(19,2) DEFAULT NULL,
  `cnf_producto_id` bigint(20) DEFAULT NULL,
  `inv_mov_almacen_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inv_tipo_mov_almacen`
--

CREATE TABLE `inv_tipo_mov_almacen` (
  `inv_tipo_mov_almacen_id` bigint(20) NOT NULL,
  `codigo_sunat` varchar(2) DEFAULT NULL,
  `naturaleza` varchar(1) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seg_accion`
--

CREATE TABLE `seg_accion` (
  `seg_accion_id` bigint(20) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `seg_accion`
--

INSERT INTO `seg_accion` (`seg_accion_id`, `descripcion`, `nombre`) VALUES
(1, '', 'VER OPCIÓN');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seg_menu`
--

CREATE TABLE `seg_menu` (
  `seg_menu_id` bigint(20) NOT NULL,
  `icon` varchar(64) DEFAULT NULL,
  `nombre` varchar(200) DEFAULT NULL,
  `path` varchar(45) DEFAULT NULL,
  `seqorder` int(11) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `seg_menu`
--

INSERT INTO `seg_menu` (`seg_menu_id`, `icon`, `nombre`, `path`, `seqorder`, `parent_id`) VALUES
(1, 'fa-user-shield', 'ADMINISTRACION', NULL, 1, NULL),
(2, 'fa-shield-alt', 'SEGURIDAD', NULL, 1, NULL),
(3, 'fa-cog', 'CONFIGURACION', NULL, 3, NULL),
(4, 'fa-box', 'INVENTARIO', NULL, 4, NULL),
(5, 'fa-cart-plus', 'VENTAS', NULL, 5, NULL),
(6, 'fa-money-bill', 'CAJA', NULL, 6, NULL),
(7, 'fa-file', 'REPORTES', NULL, 7, NULL),
(9, '', 'Región', '/region', 1, 1),
(10, '', 'Provincia', '/provincia', 2, 1),
(11, '', 'Distrito', '/distrito', 3, 1),
(12, '', 'Tipo de documento de identidad', '/tipo-documento', 4, 1),
(13, '', 'Moneda', '/moneda', 5, 1),
(14, '', 'Empresa', '/empresa', 6, 1),
(15, '', 'Unidad de medida', '/unidadmedida', 7, 1),
(16, '', 'Perfiles', '/perfil', 1, 2),
(17, '', 'Acciones', '/accion', 2, 2),
(18, '', 'Opciones de menú', '/menu', 3, 2),
(19, '', 'Permisos', '/permiso', 4, 2),
(20, '', 'Usuarios', '/usuario', 5, 2),
(21, '', 'Usuarios', '/usuario-empresa', 1, 3),
(22, '', 'Tipo de comprobante', '/tipo-comprobante', 2, 3),
(23, '', 'Numeración de comprobante', '/numcomprobante', 3, 3),
(24, '', 'Forma de pago', '/forma-pago', 4, 3),
(25, '', 'Cajas', '/caja', 5, 3),
(26, '', 'Clientes y Proveedores', '/maestro', 5, 3),
(27, '', 'Local', '/local', 1, 4),
(28, '', 'Almacén', '/almacen', 2, 4),
(29, '', 'Marca', '/marca', 3, 4),
(30, '', 'Categoría', '/categoria', 4, 4),
(31, '', 'Sub categoria', '/subcategoria', 5, 4),
(33, '', 'Producto', '/producto', 7, 4),
(34, '', 'Compra', '/compra', 8, 4),
(35, '', 'Venta', '/venta', 1, 5),
(36, '', 'Listado de ventas', '/list-ventas', 2, 5),
(37, '', 'Turno de caja', '/act-caja-turno', 1, 6),
(38, '', 'Cuentas por pagar', '/cuentas-pagar', 3, 6),
(39, '', 'Cuentas por cobrar', '/cuentas-cobrar', 4, 6),
(40, '', 'Reporte ventas', '/rpt-ventas', 1, 7),
(41, '', 'Reporte compras', '/rpt-compras', 2, 7),
(42, '', 'Stock valorizado', '/rpt-almacen', 1, 7),
(43, '', 'Kardex valorizado', '/rpt-movimiento-producto', 1, 7),
(44, '', 'Operaciones de Caja', '/caja-operacion', 2, 6),
(45, '', 'Cuentas pendientes', '/rpt-pago-programacion', 5, 7),
(46, '', 'Tipos de Movimiento', '/tipo-mov', 9, 3),
(47, '', 'Movimiento de Almacén', '/mov-almacen', 9, 4),
(48, '', 'Generación de códigos de barras', '/producto-list', 11, 4),
(49, '', 'Propia Empresa', '/empresa-empresa', 0, 3),
(50, 'fa-book', 'CONTRATO', '', 5, NULL),
(51, '', 'Contrato', '/contrato', 1, 50),
(52, '', 'Planes Contrato', '/plan-contrato', 8, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seg_permiso`
--

CREATE TABLE `seg_permiso` (
  `seg_permiso_id` bigint(20) NOT NULL,
  `seg_accion_id` bigint(20) NOT NULL,
  `seg_menu_id` bigint(20) DEFAULT NULL,
  `seg_rol_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `seg_permiso`
--

INSERT INTO `seg_permiso` (`seg_permiso_id`, `seg_accion_id`, `seg_menu_id`, `seg_rol_id`) VALUES
(2, 1, 1, 1),
(4, 1, 3, 1),
(5, 1, 4, 1),
(6, 1, 5, 1),
(7, 1, 6, 1),
(8, 1, 7, 1),
(9, 1, 9, 1),
(10, 1, 10, 1),
(11, 1, 11, 1),
(12, 1, 12, 1),
(13, 1, 13, 1),
(14, 1, 14, 1),
(21, 1, 21, 1),
(22, 1, 22, 1),
(23, 1, 23, 1),
(24, 1, 24, 1),
(25, 1, 25, 1),
(26, 1, 26, 1),
(27, 1, 27, 1),
(28, 1, 28, 1),
(29, 1, 29, 1),
(30, 1, 30, 1),
(31, 1, 31, 1),
(33, 1, 33, 1),
(34, 1, 34, 1),
(35, 1, 35, 1),
(36, 1, 36, 1),
(37, 1, 37, 1),
(38, 1, 38, 1),
(39, 1, 39, 1),
(40, 1, 40, 1),
(41, 1, 41, 1),
(42, 1, 42, 1),
(43, 1, 43, 1),
(46, 1, 4, 2),
(47, 1, 5, 2),
(48, 1, 6, 2),
(49, 1, 7, 2),
(50, 1, 21, 2),
(52, 1, 23, 2),
(53, 1, 24, 2),
(54, 1, 25, 2),
(55, 1, 26, 2),
(56, 1, 27, 2),
(57, 1, 28, 2),
(58, 1, 29, 2),
(59, 1, 30, 2),
(60, 1, 31, 2),
(62, 1, 33, 2),
(63, 1, 34, 2),
(64, 1, 35, 2),
(65, 1, 36, 2),
(66, 1, 37, 2),
(67, 1, 38, 2),
(68, 1, 39, 2),
(69, 1, 40, 2),
(70, 1, 41, 2),
(71, 1, 42, 2),
(72, 1, 43, 2),
(73, 1, 44, 2),
(74, 1, 44, 1),
(75, 1, 45, 1),
(76, 1, 45, 2),
(77, 1, 47, 2),
(78, 1, 48, 2),
(79, 1, 3, 2),
(80, 1, 49, 2),
(81, 1, 3, 3),
(82, 1, 50, 3),
(83, 1, 52, 3),
(84, 1, 6, 3),
(85, 1, 6, 3),
(86, 1, 37, 3),
(87, 1, 39, 3),
(88, 1, 7, 3),
(89, 1, 45, 3),
(90, 1, 51, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seg_rol`
--

CREATE TABLE `seg_rol` (
  `seg_rol_id` bigint(20) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `seg_rol`
--

INSERT INTO `seg_rol` (`seg_rol_id`, `descripcion`, `nombre`) VALUES
(1, NULL, 'ROLE_ADMIN'),
(2, NULL, 'ROLE_USER'),
(3, 'ASISTENTE SISTEMA PAGOS MENSUALES', 'ASISTENTE SISTEMA PAGOS MENSUALES');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seg_rol_usuario`
--

CREATE TABLE `seg_rol_usuario` (
  `seg_rol_usuario_id` bigint(20) NOT NULL,
  `cnf_empresa_id` bigint(20) DEFAULT NULL,
  `cnf_local_id` bigint(20) DEFAULT NULL,
  `seg_rol_id` bigint(20) NOT NULL,
  `seg_usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `seg_rol_usuario`
--

INSERT INTO `seg_rol_usuario` (`seg_rol_usuario_id`, `cnf_empresa_id`, `cnf_local_id`, `seg_rol_id`, `seg_usuario_id`) VALUES
(1, 1, NULL, 1, 1),
(2, 1, NULL, 2, 3),
(3, 2, NULL, 1, 4),
(6, 2, 2, 2, 7),
(7, 1, NULL, 3, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seg_usuario`
--

CREATE TABLE `seg_usuario` (
  `seg_usuario_id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `cnf_empresa` tinyblob,
  `cnf_empresa_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `seg_usuario`
--

INSERT INTO `seg_usuario` (`seg_usuario_id`, `email`, `nombre`, `password`, `estado`, `cnf_empresa`, `cnf_empresa_id`) VALUES
(1, 'edward21.sistemas@gmail.com', 'admin', '$2a$10$eJI.wQy8lvImy06/BYOHmOdRsaawezaiuwUX9Pu1GCoT.hkZ0blUW', 1, NULL, NULL),
(3, 'facturacionelectronica@opendeinsoft.com', 'antonio', '$2a$10$ySSHW/94asbWwyduiUu6t./z761Sqgo0kJA9Q0/DexTBv9wY267wu', 1, NULL, 1),
(4, 'dmra2001@gmail.com', 'diana', '$2a$10$PXjaRqL5mK1fp4kwGdtcO.i53gTzEbRnGOJUHCt4vKlVefdpks0HC', 1, NULL, 2),
(7, 'edward21.sistemas2@gmail.com', 'diana2', '$2a$10$1o1fhysFm2i/ZLZwH.mGA.g8XK4u3qT54kNomb9mPZ2/DmOo7VaZC', 1, NULL, 2),
(8, 'webpagos@gmail.com', 'edward', '$2a$10$6TDoh/HPqeT7TKwQKTnMsOD0kaTHA5JI1GZ0TBsPFi9QFgVXHbBZW', 1, NULL, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `act_caja`
--
ALTER TABLE `act_caja`
  ADD PRIMARY KEY (`act_caja_id`),
  ADD KEY `FKs7tqx0s0t8ewssexdevmacjlu` (`cnf_empresa_id`);

--
-- Indices de la tabla `act_caja_operacion`
--
ALTER TABLE `act_caja_operacion`
  ADD PRIMARY KEY (`act_caja_operacion_id`),
  ADD KEY `FKbav8sfn5slhnp22wya64g0231` (`act_caja_turno_id`),
  ADD KEY `FKanw3mv3rk3kc0q36ol26vhf1v` (`act_comprobante_id`),
  ADD KEY `FKsp5v7691hx5b3pv2cgra8uvkj` (`act_pago_id`);

--
-- Indices de la tabla `act_caja_turno`
--
ALTER TABLE `act_caja_turno`
  ADD PRIMARY KEY (`act_caja_turno_id`),
  ADD KEY `FKas73r9hj5wa2hr6m75lxxcbxt` (`act_caja_id`),
  ADD KEY `FKcr52ubs72fi6wys7654pbak92` (`seg_usuario_id`);

--
-- Indices de la tabla `act_comprobante`
--
ALTER TABLE `act_comprobante`
  ADD PRIMARY KEY (`act_comprobante_id`),
  ADD KEY `FK83tmweq77xrwlc1j5fnf4tkqx` (`cnf_forma_pago_id`),
  ADD KEY `FK2pf0fl3ord9qjaw1dswjmdqpd` (`cnf_local_id`),
  ADD KEY `FKhrcki6ddtlheihp7vetkufgac` (`cnf_maestro_id`),
  ADD KEY `FK3qyvkmexwn8uodhkt3i57q0qh` (`cnf_moneda_id`),
  ADD KEY `FKaw90oyj4cn3i56ig622bwnjx8` (`cnf_tipo_comprobante_id`),
  ADD KEY `FK3r3v7hh4gluqnk9k2dbe8956g` (`inv_almacen_id`),
  ADD KEY `FK6arrbjl983hqohyljfwsdjfqw` (`seg_usuario_id`);

--
-- Indices de la tabla `act_comprobante_detalle`
--
ALTER TABLE `act_comprobante_detalle`
  ADD PRIMARY KEY (`act_comprobante_detalle_id`),
  ADD KEY `FKilfg6eopr4fd533e9tf4ho8do` (`act_comprobante_id`),
  ADD KEY `FK3fia1ch5r7n5jmauafauaujr7` (`cnf_impuesto_condicion_id`),
  ADD KEY `FKbo85uf4txuamnlew76nrxl027` (`cnf_producto_id`);

--
-- Indices de la tabla `act_contrato`
--
ALTER TABLE `act_contrato`
  ADD PRIMARY KEY (`act_contrato_id`),
  ADD KEY `FKe4yq3usdcjs08p3142mnmrroq` (`cnf_forma_pago_id`),
  ADD KEY `FKcuhcu2sy27u2hs3dappw3x8pv` (`cnf_local_id`),
  ADD KEY `FKnqjm5whx4r22jtqh1snhv3995` (`cnf_maestro_id`),
  ADD KEY `FK4vfjkmm5ki3592h7y039v8x61` (`cnf_plan_contrato_id`),
  ADD KEY `FK5d26h5skplykajmjdqepjcour` (`cnf_tipo_comprobante_id`),
  ADD KEY `FKfogkhg5isfv3tixpm6gyidsnp` (`seg_usuario_id`),
  ADD KEY `FK3dykjscjf70uglsrhvwep6qym` (`cnf_zona_id`);

--
-- Indices de la tabla `act_pago`
--
ALTER TABLE `act_pago`
  ADD PRIMARY KEY (`act_pago_id`),
  ADD KEY `FK6ejt59l01bmg0fiqwu3ofjw30` (`seg_usuario_id`),
  ADD KEY `FKmexxerb6lbemgogm2mjrigpt3` (`cnf_tipo_comprobante_id`),
  ADD KEY `FKgb51p4xggagp03lskqyn4cd9c` (`act_pago_programacion_id`),
  ADD KEY `FKdsyo9vim783a0xl78d8n4hcp4` (`cnf_local_id`),
  ADD KEY `FKhy1ec1mk8m4kr9bi5onctwdx` (`cnf_maestro_id`);

--
-- Indices de la tabla `act_pago_detalle`
--
ALTER TABLE `act_pago_detalle`
  ADD PRIMARY KEY (`act_pago_detalle_id`),
  ADD KEY `FKrak5f4udx9pb4h2ixnr9gjv8y` (`act_pago_id`),
  ADD KEY `FKk1b6bn2mwuntt7s1qvrkdwmyy` (`act_pago_programacion_id`);

--
-- Indices de la tabla `act_pago_programacion`
--
ALTER TABLE `act_pago_programacion`
  ADD PRIMARY KEY (`act_pago_programacion_id`),
  ADD KEY `FKejdf4e15nji82ysu9hixvwbpm` (`act_contrato_id`),
  ADD KEY `FKkdlicmn4w589caxa4ijbd1ndk` (`act_comprobante_id`);

--
-- Indices de la tabla `cnf_categoria`
--
ALTER TABLE `cnf_categoria`
  ADD PRIMARY KEY (`cnf_categoria_id`),
  ADD KEY `FKcfsvsx7cuyp3lj8xijrpbv9x5` (`cnf_empresa_id`);

--
-- Indices de la tabla `cnf_distrito`
--
ALTER TABLE `cnf_distrito`
  ADD PRIMARY KEY (`cnf_distrito_id`),
  ADD KEY `FKkgspc90cqsolxnbm0vgypksq` (`cnf_provincia_id`);

--
-- Indices de la tabla `cnf_empresa`
--
ALTER TABLE `cnf_empresa`
  ADD PRIMARY KEY (`cnf_empresa_id`),
  ADD KEY `FK603327apd4cg5ywjv746lkuie` (`cnf_distrito_id`),
  ADD KEY `FK4w725qolyy4456wgwd6j91428` (`cnf_tipo_documento_id`),
  ADD KEY `FKeiieglaq04llridp9np5ormx7` (`cnf_moneda_id`);

--
-- Indices de la tabla `cnf_forma_pago`
--
ALTER TABLE `cnf_forma_pago`
  ADD PRIMARY KEY (`cnf_forma_pago_id`),
  ADD KEY `FKfd0aq5vsrte74lx08ivfw34hd` (`cnf_empresa_id`);

--
-- Indices de la tabla `cnf_forma_pago_detalle`
--
ALTER TABLE `cnf_forma_pago_detalle`
  ADD PRIMARY KEY (`cnf_forma_pago_detalle_id`),
  ADD KEY `FK8k19aemhm8r34jpt8bqsxa2jk` (`cnf_forma_pago_id`);

--
-- Indices de la tabla `cnf_impuesto_condicion`
--
ALTER TABLE `cnf_impuesto_condicion`
  ADD PRIMARY KEY (`cnf_impuesto_condicion_id`);

--
-- Indices de la tabla `cnf_local`
--
ALTER TABLE `cnf_local`
  ADD PRIMARY KEY (`cnf_local_id`),
  ADD KEY `FKdddxefeaqfpgu99haa7ipa5t8` (`cnf_empresa_id`);

--
-- Indices de la tabla `cnf_maestro`
--
ALTER TABLE `cnf_maestro`
  ADD PRIMARY KEY (`cnf_maestro_id`),
  ADD KEY `FKc98eal0m0soh2nloe1m8shbo2` (`cnf_distrito_id`),
  ADD KEY `FK6vnviydjnhj5imynhxc8oi933` (`cnf_empresa_id`),
  ADD KEY `FKn2m35srqroy4f918p30gip5m4` (`cnf_tipo_documento_id`);

--
-- Indices de la tabla `cnf_marca`
--
ALTER TABLE `cnf_marca`
  ADD PRIMARY KEY (`cnf_marca_id`),
  ADD KEY `FKmfe4irq4k2nwy3a29gqto82tx` (`cnf_empresa_id`);

--
-- Indices de la tabla `cnf_moneda`
--
ALTER TABLE `cnf_moneda`
  ADD PRIMARY KEY (`cnf_moneda_id`);

--
-- Indices de la tabla `cnf_num_comprobante`
--
ALTER TABLE `cnf_num_comprobante`
  ADD PRIMARY KEY (`cnf_num_comprobante_id`),
  ADD KEY `FKa39bau3xibiw6vgwtwstx7s6v` (`cnf_local_id`),
  ADD KEY `FK2flx8gmffshiqvuaqoqycaue7` (`cnf_tipo_comprobante_id`),
  ADD KEY `FK3md2vm1doxkatx7btdu826n1b` (`cnf_empresa_id`);

--
-- Indices de la tabla `cnf_pais`
--
ALTER TABLE `cnf_pais`
  ADD PRIMARY KEY (`cnf_pais_id`);

--
-- Indices de la tabla `cnf_plan_contrato`
--
ALTER TABLE `cnf_plan_contrato`
  ADD PRIMARY KEY (`cnf_plan_contrato_id`),
  ADD KEY `FKp661i8eni46lcsqfoo6s52re0` (`cnf_empresa_id`);

--
-- Indices de la tabla `cnf_producto`
--
ALTER TABLE `cnf_producto`
  ADD PRIMARY KEY (`cnf_producto_id`),
  ADD KEY `FKag7o5yi27gkcutba8he5ffvf` (`cnf_empresa_id`),
  ADD KEY `FK2c29b07o9841vrq3wvyx228qo` (`cnf_sub_categoria_id`),
  ADD KEY `FKnsutuyp74yvp0wryqf2hc0wy7` (`cnf_unidad_medida_id`),
  ADD KEY `FKhlmkdkxspk6mw3gj8e05vtnie` (`cnf_categoria_id`),
  ADD KEY `FKrlwymkt5tv8o7nh858hd427i8` (`cnf_marca_id`);

--
-- Indices de la tabla `cnf_provincia`
--
ALTER TABLE `cnf_provincia`
  ADD PRIMARY KEY (`cnf_provincia_id`),
  ADD KEY `FKalfo2mi4k4fuv8tism2q912qr` (`cnf_region_id`);

--
-- Indices de la tabla `cnf_region`
--
ALTER TABLE `cnf_region`
  ADD PRIMARY KEY (`cnf_region_id`),
  ADD KEY `FK7fkwd8uij874s8200xiv8w3ol` (`cnf_pais_id`);

--
-- Indices de la tabla `cnf_sub_categoria`
--
ALTER TABLE `cnf_sub_categoria`
  ADD PRIMARY KEY (`cnf_sub_categoria_id`),
  ADD KEY `FKp22c0uulveufhnf8pv2mgc290` (`cnf_categoria_id`),
  ADD KEY `FKox8qrhkx3lhfldbxqpkbyh74v` (`cnf_empresa_id`);

--
-- Indices de la tabla `cnf_tipo_comprobante`
--
ALTER TABLE `cnf_tipo_comprobante`
  ADD PRIMARY KEY (`cnf_tipo_comprobante_id`);

--
-- Indices de la tabla `cnf_tipo_documento`
--
ALTER TABLE `cnf_tipo_documento`
  ADD PRIMARY KEY (`cnf_tipo_documento_id`);

--
-- Indices de la tabla `cnf_unidad_medida`
--
ALTER TABLE `cnf_unidad_medida`
  ADD PRIMARY KEY (`cnf_unidad_medida_id`);

--
-- Indices de la tabla `cnf_zona`
--
ALTER TABLE `cnf_zona`
  ADD PRIMARY KEY (`cnf_zona_id`),
  ADD KEY `FKa4anhr8jrd5l8tfqfq4ig4jgy` (`cnf_empresa_id`);

--
-- Indices de la tabla `inv_almacen`
--
ALTER TABLE `inv_almacen`
  ADD PRIMARY KEY (`inv_almacen_id`),
  ADD KEY `FK7afksdvct38w52rqi1lcbm4hq` (`cnf_local_id`);

--
-- Indices de la tabla `inv_almacen_producto`
--
ALTER TABLE `inv_almacen_producto`
  ADD PRIMARY KEY (`inv_movimiento_producto_id`),
  ADD KEY `FKnv3w4nfot6wjt8lg4xlrmjrej` (`cnf_producto_id`),
  ADD KEY `FKqtjlnhvqjhgxrg3u23qhpsues` (`inv_almacen_id`);

--
-- Indices de la tabla `inv_movimiento_producto`
--
ALTER TABLE `inv_movimiento_producto`
  ADD PRIMARY KEY (`inv_movimiento_producto_id`),
  ADD KEY `FK7eaxfkl85cvd01timp2msd06v` (`act_comprobante_id`),
  ADD KEY `FKimit5quiwi3qxhn920bbsymat` (`cnf_producto_id`),
  ADD KEY `FKd4kmv4kr3ybwj0x0w9h281e1l` (`inv_almacen_id`),
  ADD KEY `FKddhkxbr6k35eew79rp6761q1a` (`inv_mov_almacen_id`);

--
-- Indices de la tabla `inv_mov_almacen`
--
ALTER TABLE `inv_mov_almacen`
  ADD PRIMARY KEY (`inv_mov_almacen_id`),
  ADD KEY `FKdtog7b6on41xfoupgsm4uj200` (`cnf_local_id`),
  ADD KEY `FKevp42uw9pyv5gaei6hmwe7tx6` (`cnf_maestro_id`),
  ADD KEY `FK8y7evk5qvxidlk4fijtgiivkv` (`cnf_tipo_comprobante_id`),
  ADD KEY `FK4uld20l42e3ayj8du8ltrefff` (`inv_almacen_id`),
  ADD KEY `FK9y085dxv1axg2ya2weieqh54s` (`inv_tipo_mov_almacen_id`),
  ADD KEY `FKsmqwybx2heg7s6esnanbrinjo` (`seg_usuario_id`);

--
-- Indices de la tabla `inv_mov_almacen_det`
--
ALTER TABLE `inv_mov_almacen_det`
  ADD PRIMARY KEY (`inv_mov_almacen_det_id`),
  ADD KEY `FKr0y4gusnysmxhc7kp5rppm0v7` (`cnf_producto_id`),
  ADD KEY `FKqk7ik7vphen552jmoq4kfv96y` (`inv_mov_almacen_id`);

--
-- Indices de la tabla `inv_tipo_mov_almacen`
--
ALTER TABLE `inv_tipo_mov_almacen`
  ADD PRIMARY KEY (`inv_tipo_mov_almacen_id`);

--
-- Indices de la tabla `seg_accion`
--
ALTER TABLE `seg_accion`
  ADD PRIMARY KEY (`seg_accion_id`);

--
-- Indices de la tabla `seg_menu`
--
ALTER TABLE `seg_menu`
  ADD PRIMARY KEY (`seg_menu_id`),
  ADD KEY `FKhsglity1s70tm4r3227uwdnqt` (`parent_id`);

--
-- Indices de la tabla `seg_permiso`
--
ALTER TABLE `seg_permiso`
  ADD PRIMARY KEY (`seg_permiso_id`),
  ADD KEY `FK8a9lj5w21soo2fkahri8tpr1f` (`seg_accion_id`),
  ADD KEY `FKfa974r711ngtnc4kb35r42yuj` (`seg_menu_id`),
  ADD KEY `FKeweb6vwwnerargrkbtmtga4be` (`seg_rol_id`);

--
-- Indices de la tabla `seg_rol`
--
ALTER TABLE `seg_rol`
  ADD PRIMARY KEY (`seg_rol_id`);

--
-- Indices de la tabla `seg_rol_usuario`
--
ALTER TABLE `seg_rol_usuario`
  ADD PRIMARY KEY (`seg_rol_usuario_id`),
  ADD KEY `FKgdohj8ig9hxgmonhji0agc43h` (`seg_rol_id`),
  ADD KEY `FKe8gmrgxc69mms30r85p8qw65m` (`seg_usuario_id`);

--
-- Indices de la tabla `seg_usuario`
--
ALTER TABLE `seg_usuario`
  ADD PRIMARY KEY (`seg_usuario_id`),
  ADD KEY `FK7cp75hrtj7bhauliorrkecwg9` (`cnf_empresa_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `act_caja`
--
ALTER TABLE `act_caja`
  MODIFY `act_caja_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `act_caja_operacion`
--
ALTER TABLE `act_caja_operacion`
  MODIFY `act_caja_operacion_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `act_caja_turno`
--
ALTER TABLE `act_caja_turno`
  MODIFY `act_caja_turno_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `act_comprobante`
--
ALTER TABLE `act_comprobante`
  MODIFY `act_comprobante_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `act_comprobante_detalle`
--
ALTER TABLE `act_comprobante_detalle`
  MODIFY `act_comprobante_detalle_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `act_contrato`
--
ALTER TABLE `act_contrato`
  MODIFY `act_contrato_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `act_pago`
--
ALTER TABLE `act_pago`
  MODIFY `act_pago_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `act_pago_detalle`
--
ALTER TABLE `act_pago_detalle`
  MODIFY `act_pago_detalle_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `act_pago_programacion`
--
ALTER TABLE `act_pago_programacion`
  MODIFY `act_pago_programacion_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `cnf_categoria`
--
ALTER TABLE `cnf_categoria`
  MODIFY `cnf_categoria_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `cnf_distrito`
--
ALTER TABLE `cnf_distrito`
  MODIFY `cnf_distrito_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1832;

--
-- AUTO_INCREMENT de la tabla `cnf_empresa`
--
ALTER TABLE `cnf_empresa`
  MODIFY `cnf_empresa_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `cnf_forma_pago`
--
ALTER TABLE `cnf_forma_pago`
  MODIFY `cnf_forma_pago_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `cnf_forma_pago_detalle`
--
ALTER TABLE `cnf_forma_pago_detalle`
  MODIFY `cnf_forma_pago_detalle_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `cnf_impuesto_condicion`
--
ALTER TABLE `cnf_impuesto_condicion`
  MODIFY `cnf_impuesto_condicion_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `cnf_local`
--
ALTER TABLE `cnf_local`
  MODIFY `cnf_local_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `cnf_maestro`
--
ALTER TABLE `cnf_maestro`
  MODIFY `cnf_maestro_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `cnf_marca`
--
ALTER TABLE `cnf_marca`
  MODIFY `cnf_marca_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `cnf_moneda`
--
ALTER TABLE `cnf_moneda`
  MODIFY `cnf_moneda_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `cnf_num_comprobante`
--
ALTER TABLE `cnf_num_comprobante`
  MODIFY `cnf_num_comprobante_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `cnf_pais`
--
ALTER TABLE `cnf_pais`
  MODIFY `cnf_pais_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `cnf_plan_contrato`
--
ALTER TABLE `cnf_plan_contrato`
  MODIFY `cnf_plan_contrato_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `cnf_producto`
--
ALTER TABLE `cnf_producto`
  MODIFY `cnf_producto_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `cnf_provincia`
--
ALTER TABLE `cnf_provincia`
  MODIFY `cnf_provincia_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=194;

--
-- AUTO_INCREMENT de la tabla `cnf_region`
--
ALTER TABLE `cnf_region`
  MODIFY `cnf_region_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `cnf_sub_categoria`
--
ALTER TABLE `cnf_sub_categoria`
  MODIFY `cnf_sub_categoria_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `cnf_tipo_comprobante`
--
ALTER TABLE `cnf_tipo_comprobante`
  MODIFY `cnf_tipo_comprobante_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `cnf_tipo_documento`
--
ALTER TABLE `cnf_tipo_documento`
  MODIFY `cnf_tipo_documento_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `cnf_unidad_medida`
--
ALTER TABLE `cnf_unidad_medida`
  MODIFY `cnf_unidad_medida_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;

--
-- AUTO_INCREMENT de la tabla `cnf_zona`
--
ALTER TABLE `cnf_zona`
  MODIFY `cnf_zona_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `inv_almacen`
--
ALTER TABLE `inv_almacen`
  MODIFY `inv_almacen_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `inv_almacen_producto`
--
ALTER TABLE `inv_almacen_producto`
  MODIFY `inv_movimiento_producto_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `inv_movimiento_producto`
--
ALTER TABLE `inv_movimiento_producto`
  MODIFY `inv_movimiento_producto_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `inv_mov_almacen`
--
ALTER TABLE `inv_mov_almacen`
  MODIFY `inv_mov_almacen_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `inv_mov_almacen_det`
--
ALTER TABLE `inv_mov_almacen_det`
  MODIFY `inv_mov_almacen_det_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `inv_tipo_mov_almacen`
--
ALTER TABLE `inv_tipo_mov_almacen`
  MODIFY `inv_tipo_mov_almacen_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `seg_accion`
--
ALTER TABLE `seg_accion`
  MODIFY `seg_accion_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `seg_menu`
--
ALTER TABLE `seg_menu`
  MODIFY `seg_menu_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT de la tabla `seg_permiso`
--
ALTER TABLE `seg_permiso`
  MODIFY `seg_permiso_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=91;

--
-- AUTO_INCREMENT de la tabla `seg_rol`
--
ALTER TABLE `seg_rol`
  MODIFY `seg_rol_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `seg_rol_usuario`
--
ALTER TABLE `seg_rol_usuario`
  MODIFY `seg_rol_usuario_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `seg_usuario`
--
ALTER TABLE `seg_usuario`
  MODIFY `seg_usuario_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `act_caja`
--
ALTER TABLE `act_caja`
  ADD CONSTRAINT `FKs7tqx0s0t8ewssexdevmacjlu` FOREIGN KEY (`cnf_empresa_id`) REFERENCES `cnf_empresa` (`cnf_empresa_id`);

--
-- Filtros para la tabla `act_caja_operacion`
--
ALTER TABLE `act_caja_operacion`
  ADD CONSTRAINT `FKanw3mv3rk3kc0q36ol26vhf1v` FOREIGN KEY (`act_comprobante_id`) REFERENCES `act_comprobante` (`act_comprobante_id`),
  ADD CONSTRAINT `FKbav8sfn5slhnp22wya64g0231` FOREIGN KEY (`act_caja_turno_id`) REFERENCES `act_caja_turno` (`act_caja_turno_id`),
  ADD CONSTRAINT `FKsp5v7691hx5b3pv2cgra8uvkj` FOREIGN KEY (`act_pago_id`) REFERENCES `act_pago` (`act_pago_id`);

--
-- Filtros para la tabla `act_caja_turno`
--
ALTER TABLE `act_caja_turno`
  ADD CONSTRAINT `FKas73r9hj5wa2hr6m75lxxcbxt` FOREIGN KEY (`act_caja_id`) REFERENCES `act_caja` (`act_caja_id`),
  ADD CONSTRAINT `FKcr52ubs72fi6wys7654pbak92` FOREIGN KEY (`seg_usuario_id`) REFERENCES `seg_usuario` (`seg_usuario_id`);

--
-- Filtros para la tabla `act_comprobante`
--
ALTER TABLE `act_comprobante`
  ADD CONSTRAINT `FK2pf0fl3ord9qjaw1dswjmdqpd` FOREIGN KEY (`cnf_local_id`) REFERENCES `cnf_local` (`cnf_local_id`),
  ADD CONSTRAINT `FK3qyvkmexwn8uodhkt3i57q0qh` FOREIGN KEY (`cnf_moneda_id`) REFERENCES `cnf_moneda` (`cnf_moneda_id`),
  ADD CONSTRAINT `FK3r3v7hh4gluqnk9k2dbe8956g` FOREIGN KEY (`inv_almacen_id`) REFERENCES `inv_almacen` (`inv_almacen_id`),
  ADD CONSTRAINT `FK6arrbjl983hqohyljfwsdjfqw` FOREIGN KEY (`seg_usuario_id`) REFERENCES `seg_usuario` (`seg_usuario_id`),
  ADD CONSTRAINT `FK83tmweq77xrwlc1j5fnf4tkqx` FOREIGN KEY (`cnf_forma_pago_id`) REFERENCES `cnf_forma_pago` (`cnf_forma_pago_id`),
  ADD CONSTRAINT `FKaw90oyj4cn3i56ig622bwnjx8` FOREIGN KEY (`cnf_tipo_comprobante_id`) REFERENCES `cnf_tipo_comprobante` (`cnf_tipo_comprobante_id`),
  ADD CONSTRAINT `FKhrcki6ddtlheihp7vetkufgac` FOREIGN KEY (`cnf_maestro_id`) REFERENCES `cnf_maestro` (`cnf_maestro_id`);

--
-- Filtros para la tabla `act_comprobante_detalle`
--
ALTER TABLE `act_comprobante_detalle`
  ADD CONSTRAINT `FK3fia1ch5r7n5jmauafauaujr7` FOREIGN KEY (`cnf_impuesto_condicion_id`) REFERENCES `cnf_impuesto_condicion` (`cnf_impuesto_condicion_id`),
  ADD CONSTRAINT `FKbo85uf4txuamnlew76nrxl027` FOREIGN KEY (`cnf_producto_id`) REFERENCES `cnf_producto` (`cnf_producto_id`),
  ADD CONSTRAINT `FKilfg6eopr4fd533e9tf4ho8do` FOREIGN KEY (`act_comprobante_id`) REFERENCES `act_comprobante` (`act_comprobante_id`);

--
-- Filtros para la tabla `act_contrato`
--
ALTER TABLE `act_contrato`
  ADD CONSTRAINT `FK3dykjscjf70uglsrhvwep6qym` FOREIGN KEY (`cnf_zona_id`) REFERENCES `cnf_zona` (`cnf_zona_id`),
  ADD CONSTRAINT `FK4vfjkmm5ki3592h7y039v8x61` FOREIGN KEY (`cnf_plan_contrato_id`) REFERENCES `cnf_plan_contrato` (`cnf_plan_contrato_id`),
  ADD CONSTRAINT `FK5d26h5skplykajmjdqepjcour` FOREIGN KEY (`cnf_tipo_comprobante_id`) REFERENCES `cnf_tipo_comprobante` (`cnf_tipo_comprobante_id`),
  ADD CONSTRAINT `FKcuhcu2sy27u2hs3dappw3x8pv` FOREIGN KEY (`cnf_local_id`) REFERENCES `cnf_local` (`cnf_local_id`),
  ADD CONSTRAINT `FKe4yq3usdcjs08p3142mnmrroq` FOREIGN KEY (`cnf_forma_pago_id`) REFERENCES `cnf_forma_pago` (`cnf_forma_pago_id`),
  ADD CONSTRAINT `FKfogkhg5isfv3tixpm6gyidsnp` FOREIGN KEY (`seg_usuario_id`) REFERENCES `seg_usuario` (`seg_usuario_id`),
  ADD CONSTRAINT `FKnqjm5whx4r22jtqh1snhv3995` FOREIGN KEY (`cnf_maestro_id`) REFERENCES `cnf_maestro` (`cnf_maestro_id`);

--
-- Filtros para la tabla `act_pago`
--
ALTER TABLE `act_pago`
  ADD CONSTRAINT `FK6ejt59l01bmg0fiqwu3ofjw30` FOREIGN KEY (`seg_usuario_id`) REFERENCES `seg_usuario` (`seg_usuario_id`),
  ADD CONSTRAINT `FKdsyo9vim783a0xl78d8n4hcp4` FOREIGN KEY (`cnf_local_id`) REFERENCES `cnf_local` (`cnf_local_id`),
  ADD CONSTRAINT `FKgb51p4xggagp03lskqyn4cd9c` FOREIGN KEY (`act_pago_programacion_id`) REFERENCES `act_pago_programacion` (`act_pago_programacion_id`),
  ADD CONSTRAINT `FKhy1ec1mk8m4kr9bi5onctwdx` FOREIGN KEY (`cnf_maestro_id`) REFERENCES `cnf_maestro` (`cnf_maestro_id`),
  ADD CONSTRAINT `FKmexxerb6lbemgogm2mjrigpt3` FOREIGN KEY (`cnf_tipo_comprobante_id`) REFERENCES `cnf_tipo_comprobante` (`cnf_tipo_comprobante_id`);

--
-- Filtros para la tabla `act_pago_detalle`
--
ALTER TABLE `act_pago_detalle`
  ADD CONSTRAINT `FKk1b6bn2mwuntt7s1qvrkdwmyy` FOREIGN KEY (`act_pago_programacion_id`) REFERENCES `act_pago_programacion` (`act_pago_programacion_id`),
  ADD CONSTRAINT `FKrak5f4udx9pb4h2ixnr9gjv8y` FOREIGN KEY (`act_pago_id`) REFERENCES `act_pago` (`act_pago_id`);

--
-- Filtros para la tabla `act_pago_programacion`
--
ALTER TABLE `act_pago_programacion`
  ADD CONSTRAINT `FKejdf4e15nji82ysu9hixvwbpm` FOREIGN KEY (`act_contrato_id`) REFERENCES `act_contrato` (`act_contrato_id`),
  ADD CONSTRAINT `FKkdlicmn4w589caxa4ijbd1ndk` FOREIGN KEY (`act_comprobante_id`) REFERENCES `act_comprobante` (`act_comprobante_id`);

--
-- Filtros para la tabla `cnf_categoria`
--
ALTER TABLE `cnf_categoria`
  ADD CONSTRAINT `FKcfsvsx7cuyp3lj8xijrpbv9x5` FOREIGN KEY (`cnf_empresa_id`) REFERENCES `cnf_empresa` (`cnf_empresa_id`);

--
-- Filtros para la tabla `cnf_distrito`
--
ALTER TABLE `cnf_distrito`
  ADD CONSTRAINT `FKkgspc90cqsolxnbm0vgypksq` FOREIGN KEY (`cnf_provincia_id`) REFERENCES `cnf_provincia` (`cnf_provincia_id`);

--
-- Filtros para la tabla `cnf_empresa`
--
ALTER TABLE `cnf_empresa`
  ADD CONSTRAINT `FK4w725qolyy4456wgwd6j91428` FOREIGN KEY (`cnf_tipo_documento_id`) REFERENCES `cnf_tipo_documento` (`cnf_tipo_documento_id`),
  ADD CONSTRAINT `FK603327apd4cg5ywjv746lkuie` FOREIGN KEY (`cnf_distrito_id`) REFERENCES `cnf_distrito` (`cnf_distrito_id`),
  ADD CONSTRAINT `FKeiieglaq04llridp9np5ormx7` FOREIGN KEY (`cnf_moneda_id`) REFERENCES `cnf_moneda` (`cnf_moneda_id`);

--
-- Filtros para la tabla `cnf_forma_pago`
--
ALTER TABLE `cnf_forma_pago`
  ADD CONSTRAINT `FKfd0aq5vsrte74lx08ivfw34hd` FOREIGN KEY (`cnf_empresa_id`) REFERENCES `cnf_empresa` (`cnf_empresa_id`);

--
-- Filtros para la tabla `cnf_forma_pago_detalle`
--
ALTER TABLE `cnf_forma_pago_detalle`
  ADD CONSTRAINT `FK8k19aemhm8r34jpt8bqsxa2jk` FOREIGN KEY (`cnf_forma_pago_id`) REFERENCES `cnf_forma_pago` (`cnf_forma_pago_id`);

--
-- Filtros para la tabla `cnf_local`
--
ALTER TABLE `cnf_local`
  ADD CONSTRAINT `FKdddxefeaqfpgu99haa7ipa5t8` FOREIGN KEY (`cnf_empresa_id`) REFERENCES `cnf_empresa` (`cnf_empresa_id`);

--
-- Filtros para la tabla `cnf_maestro`
--
ALTER TABLE `cnf_maestro`
  ADD CONSTRAINT `FK6vnviydjnhj5imynhxc8oi933` FOREIGN KEY (`cnf_empresa_id`) REFERENCES `cnf_empresa` (`cnf_empresa_id`),
  ADD CONSTRAINT `FKc98eal0m0soh2nloe1m8shbo2` FOREIGN KEY (`cnf_distrito_id`) REFERENCES `cnf_distrito` (`cnf_distrito_id`),
  ADD CONSTRAINT `FKn2m35srqroy4f918p30gip5m4` FOREIGN KEY (`cnf_tipo_documento_id`) REFERENCES `cnf_tipo_documento` (`cnf_tipo_documento_id`);

--
-- Filtros para la tabla `cnf_marca`
--
ALTER TABLE `cnf_marca`
  ADD CONSTRAINT `FKmfe4irq4k2nwy3a29gqto82tx` FOREIGN KEY (`cnf_empresa_id`) REFERENCES `cnf_empresa` (`cnf_empresa_id`);

--
-- Filtros para la tabla `cnf_num_comprobante`
--
ALTER TABLE `cnf_num_comprobante`
  ADD CONSTRAINT `FK2flx8gmffshiqvuaqoqycaue7` FOREIGN KEY (`cnf_tipo_comprobante_id`) REFERENCES `cnf_tipo_comprobante` (`cnf_tipo_comprobante_id`),
  ADD CONSTRAINT `FK3md2vm1doxkatx7btdu826n1b` FOREIGN KEY (`cnf_empresa_id`) REFERENCES `cnf_empresa` (`cnf_empresa_id`),
  ADD CONSTRAINT `FKa39bau3xibiw6vgwtwstx7s6v` FOREIGN KEY (`cnf_local_id`) REFERENCES `cnf_local` (`cnf_local_id`);

--
-- Filtros para la tabla `cnf_plan_contrato`
--
ALTER TABLE `cnf_plan_contrato`
  ADD CONSTRAINT `FKp661i8eni46lcsqfoo6s52re0` FOREIGN KEY (`cnf_empresa_id`) REFERENCES `cnf_empresa` (`cnf_empresa_id`);

--
-- Filtros para la tabla `cnf_producto`
--
ALTER TABLE `cnf_producto`
  ADD CONSTRAINT `FK2c29b07o9841vrq3wvyx228qo` FOREIGN KEY (`cnf_sub_categoria_id`) REFERENCES `cnf_sub_categoria` (`cnf_sub_categoria_id`),
  ADD CONSTRAINT `FKag7o5yi27gkcutba8he5ffvf` FOREIGN KEY (`cnf_empresa_id`) REFERENCES `cnf_empresa` (`cnf_empresa_id`),
  ADD CONSTRAINT `FKhlmkdkxspk6mw3gj8e05vtnie` FOREIGN KEY (`cnf_categoria_id`) REFERENCES `cnf_categoria` (`cnf_categoria_id`),
  ADD CONSTRAINT `FKnsutuyp74yvp0wryqf2hc0wy7` FOREIGN KEY (`cnf_unidad_medida_id`) REFERENCES `cnf_unidad_medida` (`cnf_unidad_medida_id`),
  ADD CONSTRAINT `FKrlwymkt5tv8o7nh858hd427i8` FOREIGN KEY (`cnf_marca_id`) REFERENCES `cnf_marca` (`cnf_marca_id`);

--
-- Filtros para la tabla `cnf_provincia`
--
ALTER TABLE `cnf_provincia`
  ADD CONSTRAINT `FKalfo2mi4k4fuv8tism2q912qr` FOREIGN KEY (`cnf_region_id`) REFERENCES `cnf_region` (`cnf_region_id`);

--
-- Filtros para la tabla `cnf_region`
--
ALTER TABLE `cnf_region`
  ADD CONSTRAINT `FK7fkwd8uij874s8200xiv8w3ol` FOREIGN KEY (`cnf_pais_id`) REFERENCES `cnf_pais` (`cnf_pais_id`);

--
-- Filtros para la tabla `cnf_sub_categoria`
--
ALTER TABLE `cnf_sub_categoria`
  ADD CONSTRAINT `FKox8qrhkx3lhfldbxqpkbyh74v` FOREIGN KEY (`cnf_empresa_id`) REFERENCES `cnf_empresa` (`cnf_empresa_id`),
  ADD CONSTRAINT `FKp22c0uulveufhnf8pv2mgc290` FOREIGN KEY (`cnf_categoria_id`) REFERENCES `cnf_categoria` (`cnf_categoria_id`);

--
-- Filtros para la tabla `cnf_zona`
--
ALTER TABLE `cnf_zona`
  ADD CONSTRAINT `FKa4anhr8jrd5l8tfqfq4ig4jgy` FOREIGN KEY (`cnf_empresa_id`) REFERENCES `cnf_empresa` (`cnf_empresa_id`);

--
-- Filtros para la tabla `inv_almacen`
--
ALTER TABLE `inv_almacen`
  ADD CONSTRAINT `FK7afksdvct38w52rqi1lcbm4hq` FOREIGN KEY (`cnf_local_id`) REFERENCES `cnf_local` (`cnf_local_id`);

--
-- Filtros para la tabla `inv_almacen_producto`
--
ALTER TABLE `inv_almacen_producto`
  ADD CONSTRAINT `FKnv3w4nfot6wjt8lg4xlrmjrej` FOREIGN KEY (`cnf_producto_id`) REFERENCES `cnf_producto` (`cnf_producto_id`),
  ADD CONSTRAINT `FKqtjlnhvqjhgxrg3u23qhpsues` FOREIGN KEY (`inv_almacen_id`) REFERENCES `inv_almacen` (`inv_almacen_id`);

--
-- Filtros para la tabla `inv_movimiento_producto`
--
ALTER TABLE `inv_movimiento_producto`
  ADD CONSTRAINT `FK7eaxfkl85cvd01timp2msd06v` FOREIGN KEY (`act_comprobante_id`) REFERENCES `act_comprobante` (`act_comprobante_id`),
  ADD CONSTRAINT `FKd4kmv4kr3ybwj0x0w9h281e1l` FOREIGN KEY (`inv_almacen_id`) REFERENCES `inv_almacen` (`inv_almacen_id`),
  ADD CONSTRAINT `FKddhkxbr6k35eew79rp6761q1a` FOREIGN KEY (`inv_mov_almacen_id`) REFERENCES `inv_mov_almacen` (`inv_mov_almacen_id`),
  ADD CONSTRAINT `FKimit5quiwi3qxhn920bbsymat` FOREIGN KEY (`cnf_producto_id`) REFERENCES `cnf_producto` (`cnf_producto_id`);

--
-- Filtros para la tabla `inv_mov_almacen`
--
ALTER TABLE `inv_mov_almacen`
  ADD CONSTRAINT `FK4uld20l42e3ayj8du8ltrefff` FOREIGN KEY (`inv_almacen_id`) REFERENCES `inv_almacen` (`inv_almacen_id`),
  ADD CONSTRAINT `FK8y7evk5qvxidlk4fijtgiivkv` FOREIGN KEY (`cnf_tipo_comprobante_id`) REFERENCES `cnf_tipo_comprobante` (`cnf_tipo_comprobante_id`),
  ADD CONSTRAINT `FK9y085dxv1axg2ya2weieqh54s` FOREIGN KEY (`inv_tipo_mov_almacen_id`) REFERENCES `inv_tipo_mov_almacen` (`inv_tipo_mov_almacen_id`),
  ADD CONSTRAINT `FKdtog7b6on41xfoupgsm4uj200` FOREIGN KEY (`cnf_local_id`) REFERENCES `cnf_local` (`cnf_local_id`),
  ADD CONSTRAINT `FKevp42uw9pyv5gaei6hmwe7tx6` FOREIGN KEY (`cnf_maestro_id`) REFERENCES `cnf_maestro` (`cnf_maestro_id`),
  ADD CONSTRAINT `FKsmqwybx2heg7s6esnanbrinjo` FOREIGN KEY (`seg_usuario_id`) REFERENCES `seg_usuario` (`seg_usuario_id`);

--
-- Filtros para la tabla `inv_mov_almacen_det`
--
ALTER TABLE `inv_mov_almacen_det`
  ADD CONSTRAINT `FKqk7ik7vphen552jmoq4kfv96y` FOREIGN KEY (`inv_mov_almacen_id`) REFERENCES `inv_mov_almacen` (`inv_mov_almacen_id`),
  ADD CONSTRAINT `FKr0y4gusnysmxhc7kp5rppm0v7` FOREIGN KEY (`cnf_producto_id`) REFERENCES `cnf_producto` (`cnf_producto_id`);

--
-- Filtros para la tabla `seg_menu`
--
ALTER TABLE `seg_menu`
  ADD CONSTRAINT `FKhsglity1s70tm4r3227uwdnqt` FOREIGN KEY (`parent_id`) REFERENCES `seg_menu` (`seg_menu_id`);

--
-- Filtros para la tabla `seg_permiso`
--
ALTER TABLE `seg_permiso`
  ADD CONSTRAINT `FK8a9lj5w21soo2fkahri8tpr1f` FOREIGN KEY (`seg_accion_id`) REFERENCES `seg_accion` (`seg_accion_id`),
  ADD CONSTRAINT `FKeweb6vwwnerargrkbtmtga4be` FOREIGN KEY (`seg_rol_id`) REFERENCES `seg_rol` (`seg_rol_id`),
  ADD CONSTRAINT `FKfa974r711ngtnc4kb35r42yuj` FOREIGN KEY (`seg_menu_id`) REFERENCES `seg_menu` (`seg_menu_id`);

--
-- Filtros para la tabla `seg_rol_usuario`
--
ALTER TABLE `seg_rol_usuario`
  ADD CONSTRAINT `FKe8gmrgxc69mms30r85p8qw65m` FOREIGN KEY (`seg_usuario_id`) REFERENCES `seg_usuario` (`seg_usuario_id`),
  ADD CONSTRAINT `FKgdohj8ig9hxgmonhji0agc43h` FOREIGN KEY (`seg_rol_id`) REFERENCES `seg_rol` (`seg_rol_id`);

--
-- Filtros para la tabla `seg_usuario`
--
ALTER TABLE `seg_usuario`
  ADD CONSTRAINT `FK7cp75hrtj7bhauliorrkecwg9` FOREIGN KEY (`cnf_empresa_id`) REFERENCES `cnf_empresa` (`cnf_empresa_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
