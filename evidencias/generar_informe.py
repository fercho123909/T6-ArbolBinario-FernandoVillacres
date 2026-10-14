from pathlib import Path
import subprocess
from xml.sax.saxutils import escape

from PIL import Image as PillowImage, ImageDraw, ImageFont
from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER
from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import mm
from reportlab.platypus import (
    Image,
    PageBreak,
    Paragraph,
    Preformatted,
    SimpleDocTemplate,
    Spacer,
    Table,
    TableStyle,
)


EVIDENCE_DIR = Path(__file__).resolve().parent
PROJECT_DIR = EVIDENCE_DIR.parent
REPORT_PATH = EVIDENCE_DIR / "Informe.pdf"
TEST_IMAGE_PATH = EVIDENCE_DIR / "salida_casos.png"
REPOSITORY_URL = "https://github.com/fercho123909/T6-ArbolBinario-FernandoVillacres"
TEST_OUTPUT_PATH = EVIDENCE_DIR / "salida_casos.txt"
SOURCE_FILES = [
    ("Anexo 1. Clase Nodo<T>", PROJECT_DIR / "src/modelo/Nodo.java"),
    ("Anexo 2. Clase ArbolBinario<T>", PROJECT_DIR / "src/negocio/ArbolBinario.java"),
    ("Anexo 3. Programa principal", PROJECT_DIR / "src/app/Main.java"),
    ("Anexo 4. Pruebas automatizadas", PROJECT_DIR / "test/PruebasArbolBinario.java"),
]


def add_page_number(canvas, document):
    canvas.saveState()
    canvas.setFont("Helvetica", 8)
    canvas.setFillColor(colors.HexColor("#52616B"))
    canvas.drawString(20 * mm, 12 * mm, "Estructura de Datos - T6 Árbol binario")
    canvas.drawRightString(190 * mm, 12 * mm, f"Página {document.page}")
    canvas.restoreState()


def make_table(rows, widths):
    table = Table(rows, colWidths=widths, repeatRows=1, hAlign="LEFT")
    table.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, 0), colors.HexColor("#183B56")),
                ("TEXTCOLOR", (0, 0), (-1, 0), colors.white),
                ("FONTNAME", (0, 0), (-1, 0), "Helvetica-Bold"),
                ("FONTNAME", (0, 1), (-1, -1), "Helvetica"),
                ("FONTSIZE", (0, 0), (-1, -1), 8),
                ("LEADING", (0, 0), (-1, -1), 10),
                ("GRID", (0, 0), (-1, -1), 0.4, colors.HexColor("#AAB7C4")),
                ("ROWBACKGROUNDS", (0, 1), (-1, -1), [colors.white, colors.HexColor("#EFF5F6")]),
                ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),
                ("TOPPADDING", (0, 0), (-1, -1), 5),
                ("BOTTOMPADDING", (0, 0), (-1, -1), 5),
            ]
        )
    )
    return table


def render_test_transcript():
    if not TEST_OUTPUT_PATH.exists():
        return False

    lines = TEST_OUTPUT_PATH.read_text(encoding="utf-8-sig").strip().splitlines()
    font = ImageFont.truetype(r"C:\Windows\Fonts\consola.ttf", 26)
    title_font = ImageFont.truetype(r"C:\Windows\Fonts\consola.ttf", 25)
    padding = 30
    line_height = 40
    image_width = 1580
    image_height = 88 + line_height * len(lines) + padding
    image = PillowImage.new("RGB", (image_width, image_height), "#101820")
    draw = ImageDraw.Draw(image)
    draw.rectangle((0, 0, image_width, 58), fill="#183B56")
    draw.text((padding, 12), "SALIDA REAL DE LAS PRUEBAS AUTOMATIZADAS", font=title_font, fill="#FFFFFF")

    for index, line in enumerate(lines):
        draw.text((padding, 72 + index * line_height), line, font=font, fill="#E7F0F2")

    image.save(TEST_IMAGE_PATH)
    return True


def build_report():
    has_test_image = render_test_transcript()
    styles = getSampleStyleSheet()
    styles.add(
        ParagraphStyle(
            name="ReportTitle",
            parent=styles["Title"],
            fontName="Helvetica-Bold",
            fontSize=21,
            leading=26,
            textColor=colors.HexColor("#183B56"),
            alignment=TA_CENTER,
            spaceAfter=5 * mm,
        )
    )
    styles.add(
        ParagraphStyle(
            name="Section",
            parent=styles["Heading2"],
            fontName="Helvetica-Bold",
            fontSize=13,
            leading=16,
            textColor=colors.HexColor("#087E8B"),
            spaceBefore=3 * mm,
            spaceAfter=2 * mm,
        )
    )
    styles.add(
        ParagraphStyle(
            name="BodySpanish",
            parent=styles["BodyText"],
            fontName="Helvetica",
            fontSize=9,
            leading=12,
            spaceAfter=2 * mm,
        )
    )
    styles.add(
        ParagraphStyle(
            name="CodeTree",
            fontName="Courier",
            fontSize=8,
            leading=9.5,
            leftIndent=5 * mm,
            spaceAfter=2 * mm,
        )
    )
    styles.add(
        ParagraphStyle(
            name="SourceCode",
            fontName="Courier",
            fontSize=6.2,
            leading=7.3,
        )
    )

    document = SimpleDocTemplate(
        str(REPORT_PATH),
        pagesize=A4,
        rightMargin=20 * mm,
        leftMargin=20 * mm,
        topMargin=17 * mm,
        bottomMargin=19 * mm,
        title="Informe de guía práctica: Implementación de un árbol binario",
        author="Villacres Lopez Walter Fernando",
    )

    story = [
        Paragraph("UNIVERSIDAD TÉCNICA DE AMBATO", styles["Heading2"]),
        Paragraph("Facultad de Ingeniería en Sistemas, Electrónica e Industrial", styles["BodySpanish"]),
        Paragraph("Carrera de Software · Ciclo académico julio-diciembre 2026", styles["BodySpanish"]),
        Spacer(1, 9 * mm),
        Paragraph("INFORME DE GUÍA PRÁCTICA", styles["ReportTitle"]),
        Paragraph(
            "T6. Implementación de un árbol binario en Java - Sistema de organización jerárquica de departamentos universitarios",
            styles["Heading3"],
        ),
        Spacer(1, 4 * mm),
        make_table(
            [
                ["Dato", "Información"],
                ["Unidad curricular", "Profesional"],
                ["Nivel y paralelo", '3ro Software "B"'],
                ["Modalidad", "Individual"],
                ["Estudiante", "Villacres Lopez Walter Fernando"],
                ["Asignatura", "Estructura de Datos"],
                ["Docente", "Ing. José Caiza"],
                ["Repositorio", escape(REPOSITORY_URL)],
            ],
            [45 * mm, 125 * mm],
        ),
        Paragraph("Objetivo", styles["Section"]),
        Paragraph(
            "Implementé un árbol binario con programación orientada a objetos para insertar, consultar y analizar la jerarquía de dependencias universitarias.",
            styles["BodySpanish"],
        ),
        Paragraph("Recursos y actividades", styles["Section"]),
        Paragraph(
            "Usé Java (JDK), Visual Studio Code, Git, GitHub, plataformas y aplicaciones educativas. La inteligencia artificial se empleó como apoyo. Implementé Nodo<T>, las operaciones recursivas, el menú y los cinco casos obligatorios. La práctica fue individual.",
            styles["BodySpanish"],
        ),
        Paragraph("Árbol inicial", styles["Section"]),
        Preformatted(
            "UNIVERSIDAD\n"
            "|-- [I] FISEI\n"
            "|   |-- [I] SOFTWARE\n"
            "|   |   |-- [I] NIVEL1\n"
            "|   |   `-- [D] NIVEL3\n"
            "|   `-- [D] TI\n"
            "`-- [D] DIDE\n"
            "    |-- [I] INVESTIGACIÓN\n"
            "    `-- [D] VINCULACIÓN",
            styles["CodeTree"],
        ),
        Paragraph(
            "El árbol tiene 9 nodos, 5 hojas y altura 3 en aristas. [I] representa hijo izquierdo y [D] hijo derecho.",
            styles["BodySpanish"],
        ),
        PageBreak(),
        Paragraph("Diseño, resultados y rúbrica", styles["Section"]),
        Paragraph(
            "Organicé el programa en los paquetes modelo, negocio y app. Nodo<T> guarda el dato y sus hijos; ArbolBinario<T> valida inserciones y calcula recursivamente nodos, hojas y altura; Main usa Scanner y ofrece las opciones 1 a 9.",
            styles["BodySpanish"],
        ),
        make_table(
            [
                ["Nodo", "Grado", "Hoja", "Hijos"],
                ["UNIVERSIDAD", "2", "No", "FISEI, DIDE"],
                ["FISEI", "2", "No", "SOFTWARE, TI"],
                ["DIDE", "2", "No", "INVESTIGACIÓN, VINCULACIÓN"],
                ["SOFTWARE", "2", "No", "NIVEL1, NIVEL3"],
                ["TI, INVESTIGACIÓN, VINCULACIÓN, NIVEL1, NIVEL3", "0", "Sí", "-"],
            ],
            [65 * mm, 20 * mm, 20 * mm, 65 * mm],
        ),
        Paragraph("Casos obligatorios", styles["Section"]),
        make_table(
            [
                ["Caso", "Esperado", "Obtenido"],
                ["Árbol vacío", "0 nodos; altura -1", "0 nodos; altura -1"],
                ["Un nodo", "1 nodo; 1 hoja; altura 0", "1 nodo; 1 hoja; altura 0"],
                ["Árbol inicial", "9 nodos; 5 hojas; altura 3", "9 nodos; 5 hojas; altura 3"],
                ["Inserción ocupada", "Rechazada; estructura intacta", "Rechazada; quedan 9 nodos"],
                ["Grados", "FISEI=2; SOFTWARE=2", "FISEI=2; SOFTWARE=2"],
            ],
            [38 * mm, 66 * mm, 66 * mm],
        ),
        Paragraph("Complejidad", styles["Section"]),
        make_table(
            [
                ["Operación", "Tiempo", "Espacio"],
                ["contarNodos, contarHojas, altura", "O(n)", "O(h)"],
                ["buscar", "O(n) peor caso", "O(h)"],
                ["agregarIzquierdo/agregarDerecho", "O(1)", "O(1)"],
                ["esVacio, esHoja, grado", "O(1)", "O(1)"],
            ],
            [90 * mm, 40 * mm, 40 * mm],
        ),
        Paragraph(
            "n es el número de nodos y h la altura. La búsqueda es O(n) porque el árbol de departamentos no tiene un orden de búsqueda.",
            styles["BodySpanish"],
        ),
        Paragraph("Compilación", styles["Section"]),
        Preformatted(
            "javac -encoding UTF-8 -d out src/modelo/Nodo.java src/negocio/ArbolBinario.java src/app/Main.java\n"
            "java -cp out app.Main",
            styles["CodeTree"],
        ),
        PageBreak(),
        Paragraph("Evidencia 1. Menú y visualización", styles["Section"]),
    ]

    menu_image = EVIDENCE_DIR / "captura_ejecucion.png"
    if menu_image.exists():
        story.append(Image(str(menu_image), width=170 * mm, height=95 * mm, kind="proportional"))
        story.append(Paragraph("Captura real de la consola del programa mostrando el menú y el árbol con sus lados.", styles["BodySpanish"]))
    else:
        story.append(Paragraph("Falta la captura de menú y árbol.", styles["BodySpanish"]))

    story.extend([PageBreak(), Paragraph("Evidencia 2. Casos de prueba", styles["Section"])])
    if TEST_OUTPUT_PATH.exists():
        test_output = TEST_OUTPUT_PATH.read_text(encoding="utf-8-sig")
        if has_test_image:
            story.append(Image(str(TEST_IMAGE_PATH), width=170 * mm, height=55 * mm, kind="proportional"))
            story.append(
                Paragraph(
                    "Vista visual preparada desde la transcripción real de la ejecución; no es una captura directa de pantalla.",
                    styles["BodySpanish"],
                )
            )
        story.append(Preformatted(test_output, styles["CodeTree"], maxLineLength=110))
        story.append(Paragraph("Transcripción guardada directamente desde la ejecución del ejecutor automatizado.", styles["BodySpanish"]))
        story.append(Paragraph("Para una captura de pantalla adicional, ejecuto evidencias/demo_pruebas.cmd y guardo la ventana antes de cerrarla.", styles["BodySpanish"]))
    else:
        story.append(Paragraph("Todavía no se ha guardado la salida de las pruebas.", styles["BodySpanish"]))

    story.extend(
        [
            PageBreak(),
            Paragraph("Reflexiones", styles["Section"]),
            Paragraph(
                "1. La altura cuenta las aristas del camino más largo desde la raíz hasta una hoja. En mi árbol, UNIVERSIDAD → FISEI → SOFTWARE → NIVEL1 tiene 3 aristas; las ramas más cortas no cambian ese valor. En el método recursivo se compara la altura de ambos hijos con Math.max.",
                styles["BodySpanish"],
            ),
            Paragraph(
                "2. Una lista enlazada es lineal y para buscar suele recorrerse nodo por nodo, con costo O(n). Un árbol binario representa relaciones jerárquicas. Si está ordenado y equilibrado, una búsqueda puede ser O(log n); si se degenera en una rama, vuelve a O(n). El árbol de esta práctica no está ordenado, así que su búsqueda es O(n).",
                styles["BodySpanish"],
            ),
            Paragraph(
                "3. En SmartETL podría usar un árbol de decisión para validar registros, con cada condición enviando el dato por una rama. También se podría indexar por una clave con un árbol binario de búsqueda equilibrado, o representar expresiones de transformación. Para muchos registros convendría evaluar AVL o rojo-negro.",
                styles["BodySpanish"],
            ),
            Paragraph("Conclusiones y recomendaciones", styles["Section"]),
            Paragraph(
                "Separar modelo, negocio y app ayudó a organizar la solución. La recursividad simplificó los conteos y la altura, y las pruebas confirmaron los casos límite. Recomiendo probar siempre árbol vacío y unitario, validar inserciones y, para búsquedas masivas, evaluar árboles equilibrados.",
                styles["BodySpanish"],
            ),
            Paragraph("Habilidades que practiqué", styles["Section"]),
            Paragraph("Marqué pensamiento crítico, adaptabilidad y responsabilidad durante el desarrollo de esta práctica.", styles["BodySpanish"]),
            Paragraph("Control de versiones", styles["Section"]),
        ]
    )

    commits = subprocess.run(
        ["git", "-C", str(PROJECT_DIR), "log", "--reverse", "--pretty=format:%h %s"],
        check=True,
        capture_output=True,
        text=True,
    ).stdout.splitlines()
    commit_rows = [["Commit", "Cambio"]]
    for commit in commits[:4]:
        short_hash, message = commit.split(" ", 1)
        commit_rows.append([short_hash, message])
    story.append(make_table(commit_rows, [25 * mm, 145 * mm]))
    story.extend(
        [
            Paragraph("Referencias", styles["Section"]),
            Paragraph(
                "[1] T. H. Cormen et al., Introduction to Algorithms, 4.ª ed., MIT Press, 2022.<br/>"
                "[2] M. A. Weiss, Data Structures and Algorithm Analysis in Java, 3.ª ed., Pearson, 2012.<br/>"
                "[3] R. Sedgewick y K. Wayne, Algorithms, 4.ª ed., Addison-Wesley, 2011.<br/>"
                "[4] Oracle, Java Platform, Standard Edition Documentation, https://docs.oracle.com/en/java/.<br/>"
                f'[5] F. Villacres, "T6-ArbolBinario-FernandoVillacres," GitHub, 2026. <link href="{REPOSITORY_URL}" color="#087E8B">Repositorio</link>.',
                styles["BodySpanish"],
            ),
            Paragraph("Anexos: código fuente", styles["Section"]),
        ]
    )

    for title, source_path in SOURCE_FILES:
        story.extend([PageBreak(), Paragraph(escape(title), styles["Section"])])
        if source_path.exists():
            source = source_path.read_text(encoding="utf-8")
            story.append(Preformatted(source, styles["SourceCode"], maxLineLength=135))
        else:
            story.append(Paragraph(f"No se encontró {escape(str(source_path.relative_to(PROJECT_DIR)))}.", styles["BodySpanish"]))

    document.build(story, onFirstPage=add_page_number, onLaterPages=add_page_number)
    print(f"PDF generado: {REPORT_PATH}")


if __name__ == "__main__":
    build_report()