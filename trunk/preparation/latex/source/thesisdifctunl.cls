%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%% thesisdifctunl.cls
%%% DI/FCT/UNL Dissertation document class
%%%
%%% IMPORTANT: THIS IS BASED ON A TEMPLATE FROM THE UNIVERSITY OF PERNAMBUCO
%%%  STILL UNDER DEVELOPMENT
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{thesisdifctunl}[2007/12/30]

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% OPTIONS 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\DeclareOption{pt}{%
  \let\@language=0%
  \PassOptionsToPackage{portuges}{babel}}

\DeclareOption{en}{%
  \let\@language=1%
  \PassOptionsToPackage{portuges,english}{babel}}

\DeclareOption{oneside}{%
  \PassOptionsToClass{oneside}{book}}

\DeclareOption{twoside}{%
  \PassOptionsToClass{twoside}{book}}
 
\DeclareOption{print}{%
  \let\@scr=0}

\DeclareOption{scr}{%
  \let\@scr=1%
  \PassOptionsToClass{dvipdfm}{book}}
  
\DeclareOption{lei}{%
  \let\@degreetype=0}

\DeclareOption{premei}{%
  \let\@degreetype=1}
  
\DeclareOption{mei}{%
  \let\@degreetype=2}

\DeclareOption{prop}{%
  \let\@degreetype=3}
  
\DeclareOption{prephd}{%
  \let\@degreetype=4}

\DeclareOption{phd}{%
  \let\@degreetype=5}
 
% Default options
\ExecuteOptions{pt,twoside,premei,print}
\ProcessOptions

\LoadClass[12pt,a4paper]{book}


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% PACKAGES
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\RequirePackage{amsmath,amsfonts,amssymb,amsthm}
\RequirePackage{babel}
\RequirePackage{setspace}
\RequirePackage{calc}
\RequirePackage{ifthen}
\RequirePackage[latin1]{inputenc}
\RequirePackage{textcase}
\RequirePackage{textcomp}
\RequirePackage{url}
\RequirePackage{xspace}

  \RequirePackage[T1]{fontenc}
  \RequirePackage{mathptmx}
  \RequirePackage[scaled=0.9]{helvet}
  \RequirePackage{courier}
  \RequirePackage{times}

\if\@scr0
  \RequirePackage{graphicx}
\fi
\if\@scr1
  \RequirePackage[dvips]{graphicx}
  \RequirePackage[dvips,usenames]{color}
  \RequirePackage[dvipdfm,colorlinks,backref]{hyperref}
\fi


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% GENERAL PURPOSE MACROS
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\let\origcleardoublepage=\cleardoublepage
\def\cleardoublepage{%
  \newpage{\pagestyle{empty}\origcleardoublepage}
}

%%
% For use with the pseudocode package
\def\@lopcchapterspace{\relax}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% LABELS
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% Language Independent

\gdef\@maleadvisertitle{Orientador}
\gdef\@femaleadvisertitle{Orientadora}
\gdef\@malecoadvisertitle{Co-orientador}
\gdef\@femalecoadvisertitle{Co-orientadora}
\gdef\@bachelordissertation{Trabalho de Licenciatura}
\gdef\@mastersdissertation{Dissertação de Mestrado}
\gdef\@mastersqualifying{Preparação da Dissertação}
\gdef\@phdqualifying{Preparação da Tese}
\gdef\@phdproposal{Proposta de Tese de Doutoramento}
\gdef\@phdthesis{Tese de Doutoramento}
\gdef\@bachelordegree{Licenciado}
\gdef\@mastersdegree{Mestre}
\gdef\@phddegree{Doutor}
\gdef\@presentationtext{%
Trabalho apresentado no âmbito d\if\@degreetype0 a \else o \fi
\@program, como requisito parcial para obtenção do
grau de \@degree\ em \@majorfield.}
\gdef\resumoname{Resumo}
\gdef\abstrname{Abstract}
\gdef\keywordsnamePT{Palavras-chave}
\gdef\keywordsnameEN{Keywords}

%% Language Dependent

% Portuguese
\if\@language0
  \gdef\@notdefined{NÃO DEFINIDO}
  \gdef\acknowledgementsname{Agradecimentos}
  \gdef\@axiomname{Axioma}
  \gdef\@conjecturename{Conjectura}
  \gdef\@defname{Definição}
  \gdef\@lemmaname{Lema}
  \gdef\@theoname{Teorema}
  \gdef\@propname{Proposição}
  \gdef\@corname{Corolário}
  \gdef\@proofname{Prova}
  \gdef\@figurename{Figura}
  \gdef\@tablename{Tabela}
  \gdef\@equationame{equação}
  \gdef\@chaptername{Capítulo}
  \gdef\@sectionname{Secção}
  \gdef\@appendixname{Apêndice}
  \gdef\@pagename{página}
% English
\else\if\@language1
  \gdef\@notdefined{UNDEFINED}
  \gdef\acknowledgementsname{Acknowledgements}
  \gdef\@axiomname{Axiom}
  \gdef\@conjecturename{Conjecture}
  \gdef\@defname{Definition}
  \gdef\@lemmaname{Lemma}
  \gdef\@theoname{Theorem}
  \gdef\@propname{Proposition}
  \gdef\@corname{Corollary}
  \gdef\@proofname{Proof}
  \gdef\@figurename{Figure}
  \gdef\@tablename{Table}
  \gdef\@equationame{equation}
  \gdef\@chaptername{Chapter}
  \gdef\@sectionname{Section}
  \gdef\@appendixname{Appendix}
  \gdef\@pagename{page}
  
\fi\fi


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% IDENTIFICATION
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% School identification

\def\university#1{%
  \gdef\@university{#1}}
\def\@university{Universidade Nova de Lisboa}

\def\universitylogo{logounl.tex}

\let\@showfaculty=0
\def\faculty#1{%
  \let\@showfaculty=1
  \gdef\@faculty{#1}}
\def\@faculty{Faculdade de Ciências e Tecnologia}

\let\@showdepartment=0
\def\department#1{%
  \let\@showdepartment=1
  \gdef\@department{#1}}
\def\@department{Departamento de Informática}

\def\program#1{%
  \gdef\@program{#1}}
\def\@program{\@notdefined}

\def\majorfield#1{%
  \gdef\@majorfield{#1}}
\def\@majorfield{\@notdefined}

\def\address#1{%
  \gdef\@address{#1}}
\def\@address{Lisboa}


%% Authors identification

\def\author#1{%
  \gdef\@author{#1}
  \if\@scr1 \hypersetup{pdfauthor={\@author}}\fi}
\def\@author{\@notdefined}

\def\adviser{%
  \@ifnextchar [%
    {\@padviser}%
    {\@padviser[\@empty]}}
\def\@padviser[#1]#2{%
  \ifx#1\@empty
    \gdef\@advisertitle{\@maleadvisertitle}
  \else
    \gdef\@advisertitle{\@femaleadvisertitle}
  \fi
  \gdef\@adviser{#2}}
\def\@adviser{\@notdefined}

\let\@showcoadviser=0
\def\coadviser{%
  \@ifnextchar [%
    {\@pcoadviser}%
    {\@pcoadviser[\@empty]}}
\def\@pcoadviser[#1]#2{%
  \let\@showcoadviser=1
  \ifx#1\@empty
    \gdef\@coadvisertitle{\@malecoadvisertitle}
  \else
    \gdef\@coadvisertitle{\@femalecoadvisertitle}
  \fi
  \gdef\@coadviser{#2}}

%% Work identification

\def\title#1{%
  \gdef\@title{#1}
  \if\@scr1 \hypersetup{pdftitle={\@title}}\fi}
\def\@title{\@notdefined}

\def\@texttype{%
  \if\@degreetype0
    \@bachelordissertation
  \else\if\@degreetype1
    \@mastersqualifying
  \else\if\@degreetype2
  \@mastersdissertation 
  \else\if\@degreetype3
    \@phdproposal
  \else\if\@degreetype4
   \@phdqualifying
   \else\if\@degreetype5
    \@phdthesis
  \fi\fi\fi\fi\fi}

\def\@degree{%
  \if\@degreetype0
    \@bachelordegree
  \else\if\@degreetype1
    \@mastersdegree
  \else\if\@degreetype2
    \@mastersdegree
  \else\if\@degreetype3
    \@phddegree
  \else\if\@degreetype4
    \@phddegree
   \else\if\@degreetype4
    \@phddegree
  \fi\fi\fi\fi\fi}


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% PAGE LAYOUT
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\setlength{\topmargin}{1cm}
\setlength{\headheight}{1cm}
\setlength{\headsep}{0.5cm}

\setlength{\textheight}{21cm}
\setlength{\topskip}{0.5cm}
\setlength{\textwidth}{16cm}
\setlength{\footskip}{0.5cm}

\setlength{\oddsidemargin}{0cm}
\setlength{\evensidemargin}{0cm}

\setlength{\marginparwidth}{10mm}
\setlength{\marginparsep}{10mm}

\brokenpenalty=10000


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Fonts
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\newcommand\quotefont{\normalfont\normalsize}
\newcommand\quotefonti{\it\normalsize}


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Frontpage
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\def\frontpage{%
  \if@openright\cleardoublepage\else\clearpage\fi
  \thispagestyle{empty}
  \begin{center}
  \input\universitylogo
  \large \\
  \@university
  \if\@showfaculty1\\\@faculty\fi
  \if\@showdepartment1\\\@department\fi
  \vskip 2.0cm
  \@texttype \vskip\baselineskip \@program
  \vskip 2.0cm
  \begin{minipage}{160mm}
    \begin{center}
      {\Large\bfseries\@title}
      \vskip 1cm
      \@author
    \end{center}
  \end{minipage}\\
  \vfill
 % \@address\\
  \smallskip
	\@date
  \end{center}
}


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Presentation page
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\def\presentationpage{%
  \if@openright\cleardoublepage\else\clearpage\fi
  \thispagestyle{empty}
  \begin{center}
  \input\universitylogo
  \large
  \\ 
  \@university
  \if\@showfaculty1\\\@faculty\fi
  \if\@showdepartment1\\\@department\fi
  \vskip 2.0cm
  \@texttype
  \vskip 2.0cm
   {\Large\bfseries\@title}
  \vskip 1cm
  \@author
  \vskip\baselineskip
  %\begin{flushright}
    \begin{minipage}{100mm}
    \begin{center}
    \begin{tabular}{rl}
    {\quotefont \@advisertitle:}&\@adviser\\
    \if\@showcoadviser1{\quotefont\@coadvisertitle:}&\@coadviser\\\fi
    \end{tabular}
    \end{center}
    \vskip 0.5cm
    \quotefonti %
    \@presentationtext
    \end{minipage}
  %\end{flushright}
  \vfill
  %\@address\\
	\smallskip
  \@date
  \end{center}
}


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Dedicatory
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\def\dedicatory{%
  \if@openright\cleardoublepage\else\clearpage\fi
  \thispagestyle{empty}
  ~\\
  \vfill
  \begin{flushright}
    \begin{minipage}{100mm}
    \quotefonti
    \begin{flushright}}
\def\enddedicatory{
\end{flushright}
    \normalfont
    \end{minipage}
  \end{flushright}}


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Acknowledgements
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\def\acknowledgements{%
  \chapter*{\acknowledgementsname}}


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Resumo
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\def\resumo{%
  \gdef\@keywordsname{\keywordsnamePT}
  \chapter*{\resumoname}
   \rule{\textwidth}{0.2mm} ~\\
  }


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Abstract
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\def\abstract{%
  \gdef\@keywordsname{\keywordsnameEN}
  \chapter*{\abstrname}
  \rule{\textwidth}{0.2mm} ~\\ 
   }

  
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Keywords
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\def\@keywordsname{\@defaultkeywordsname}
\def\keywords{%
  \par\vskip\baselineskip\noindent{\bf\@keywordsname: }}
\def\endkeywords{}


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Quotations
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\def\epigraph{%
  \if@openright\cleardoublepage\else\clearpage\fi
  \thispagestyle{empty}
  ~\\\vfill
  \begin{quotation}}
\def\endepigraph{\end{quotation}}

\def\quotation{%
  \@ifnextchar [%
    {\begin{pquot@tion}}%
    {\begin{pquot@tion}[\@empty]}}
\def\endquotation{\end{pquot@tion}\@afterindentfalse\@afterheading}

\def\pquot@tion[#1]#2{%
  \def\@qauthor{#2}
  \def\@qnote{#1}
  \begin{flushright}
    \begin{minipage}{0.8\textwidth}
      \begin{flushright}\quotefonti}
\def\endpquot@tion{%
        \vskip.2\baselineskip%
        \quotefont---\MakeTextUppercase{\@qauthor}
        \if\@qnote\@empty
          \relax
        \else
          \space(\@qnote)
        \fi
      \end{flushright}
    \end{minipage}
  \end{flushright}
  \normalfont\vskip2\baselineskip}

  
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Table of contents
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\def\@plaintocline#1#2#3#4#5{%
  \ifnum #1>\c@tocdepth \else
    \vskip \z@ \@plus.2\p@
    {\leftskip #2\relax \rightskip \@tocrmarg \parfillskip -\rightskip
     \parindent #2\relax\@afterindenttrue
     \interlinepenalty\@M
     \leavevmode
     \@tempdima #3\relax
     \advance\leftskip \@tempdima \null\nobreak\hskip -\leftskip
     {#4}\nobreak
     \leaders\hbox{$\m@th
        \mkern \@dotsep mu\hbox{}\mkern \@dotsep
        mu$}\hfill
     \nobreak
     \hb@xt@\@pnumwidth{\hfil\normalfont \normalcolor #5}%
     \par}%
  \fi}

 \renewcommand\tableofcontents{%
   \chapter*{\contentsname}
   \@starttoc{toc}}
 

\def\l@chapter#1#2{%
  \ifnum \c@tocdepth >\m@ne
    \addpenalty{-\@highpenalty}%
    \vskip .75em \@plus\p@
    \setlength\@tempdima{1.5em}%
    \begingroup
      \parindent \z@ \rightskip \@pnumwidth
      \parfillskip -\@pnumwidth
      \leavevmode \bfseries
      \advance\leftskip\@tempdima
      \hskip -\leftskip
      #1\nobreak\hfil \nobreak\hb@xt@\@pnumwidth{\hss #2}\par
      \penalty\@highpenalty
    \endgroup 
  \fi}

\let\@dottedtocline\@plaintocline

\setcounter{tocdepth}{3}


\renewcommand\listoffigures{%
   \chapter*{\listfigurename}
   \@starttoc{lof}}

\renewcommand\listoftables{%
   \chapter*{\listtablename}
   \@starttoc{lot}}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Sectioning
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\setcounter{secnumdepth}{4}

\def\part{%
\if@openright\cleardoublepage\else\clearpage\fi
  \thispagestyle{empty}%
  \secdef\@part\@spart}
\def\@part[#1]#2{%
    \ifnum \c@secnumdepth >-2\relax
      \refstepcounter{part}%
      \addcontentsline{toc}{part}{\thepart\hspace{1em}#1}%
    \else
      \addcontentsline{toc}{part}{#1}%
    \fi
    \markboth{}{}%
    {\centering
     \interlinepenalty \@M
     \normalfont
     \null\vfil
     \ifnum \c@secnumdepth >-2\relax
       {\sc\Large\partname\nobreakspace\thepart}
       \par
       \vskip 20\p@
     \fi
     {\huge\bfseries #2\par}}
     \vfil}
\def\@spart#1{%
    {\centering
     \interlinepenalty \@M
     \normalfont
     \null\vfil
     {\huge\bfseries #1\par}}
     \vfil}

\def\chapter{\if@openright\cleardoublepage\else\clearpage\fi
             \thispagestyle{plain}%
             \global\@topnum\z@
             \@afterindentfalse
             \secdef\@chapter \@schapter
             }

\def\@chapter[#1]#2{
   \refstepcounter{chapter}%
   \addcontentsline{toc}{chapter}{\protect\numberline{\thechapter}#1}%
   \chaptermark{#1}%
   \addtocontents{lof}{\protect\addvspace{10\p@}}%
   \addtocontents{lot}{\protect\addvspace{10\p@}}%
   \@lopcchapterspace%
    \@makechapterhead{#2}%
   \@afterheading}

\def\@makechapterhead#1{
  \relax
  \begingroup
  \Large\bfseries
     %\chaptername
     {\LARGE \thechapter}~.\hspace{0.3cm}~#1\par \endgroup
  \skip@3pc \advance\skip@-\normalbaselineskip
  \vskip\skip@ }  
  
\def\@schapter#1{%
  \schaptermark{#1}%
  \@makeschapterhead{#1}%
  \@afterheading}

\def\@makeschapterhead#1{
\relax
  \begingroup
  \Large \bfseries
  #1\par \endgroup
  \skip@3pc \advance\skip@-\normalbaselineskip
  \vskip\skip@ }

\def\appendix{%
   \setcounter{chapter}{0}%
   \renewcommand{\thechapter}{\Alph{chapter}}%
   \renewcommand{\chaptername}{\appendixname}}

\def\@startsection#1#2#3#4#5#6{%
 \if@noskipsec \leavevmode \fi
 \par \@tempskipa #4\relax
 \@afterindentfalse
 \ifdim \@tempskipa <\z@ \@tempskipa -\@tempskipa \@afterindentfalse\fi
 \if@nobreak \everypar{}\else
     \addpenalty\@secpenalty\addvspace\@tempskipa\fi
 \@ifstar{\@dblarg{\@sect{#1}{\@m}{#3}{#4}{#5}{#6}}}%
         {\@dblarg{\@sect{#1}{#2}{#3}{#4}{#5}{#6}}}%
}

\def\section{%
  \@startsection{section}{1}{0mm}{1.5\baselineskip}
    {\baselineskip}{\large\bfseries}}

\def\subsection{%
  \@startsection{subsection}{2}{0mm}{1.2\baselineskip}
    {.6\baselineskip}{\bfseries}}

\def\subsubsection{%
  \@startsection{subsubsection}{3}{0mm}{\baselineskip}
   {.6\baselineskip}{\normalfont}}

\def\paragraph{%
  \@startsection{paragraph}{4}{0mm}{\baselineskip}
   {-1em}{\itshape}}



%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Headers & footers
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\def\@evenhead{\rlap{\thepage}\hfill{\leftmark}\hfill}%
\def\@oddhead{\hfill{\rightmark}\hfill\llap{\thepage}}%

%% TO DO IN THE FUTURE
\def\chaptermark#1%
{\markboth{}{}}

\def\schaptermark#1%
{\markboth{}{}}

\def\sectionmark#1%
{\markboth{}{}}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Bibliography
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\global\renewenvironment{thebibliography}[1]
     {\chapter*{\bibname}%
      \list{\@biblabel{\@arabic\c@enumiv}}%
           {\settowidth\labelwidth{\@biblabel{#1}}%
            \leftmargin\labelwidth
            \advance\leftmargin\labelsep
            \@openbib@code
            \usecounter{enumiv}%
            \let\p@enumiv\@empty
            \renewcommand\theenumiv{\@arabic\c@enumiv}}%
      \sloppy
      \clubpenalty4000
      \@clubpenalty \clubpenalty
      \widowpenalty4000%
      \sfcode`\.\@m}
     {\def\@noitemerr
       {\@latex@warning{Empty `thebibliography' environment}}%
      \endlist}


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Tables and figures
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\long\def\@makecaption#1#2{%
  \vskip\abovecaptionskip
  \sbox\@tempboxa{\small\bf #1\rm\enskip #2}%
  \ifdim \wd\@tempboxa >\hsize
    {\small\bf#1\rm\enskip #2\par}
  \else
    \global \@minipagefalse
    \hb@xt@\hsize{\hfil\box\@tempboxa\hfil}%
  \fi
  \vskip\belowcaptionskip}


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Mathematics
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Equation numbering
\renewcommand{\theequation}{\rm\thechapter.\arabic{equation}}

% Theorem-like environments
\newtheoremstyle{theo}%
{\topsep}{\topsep}% Space above and below
{\slshape}% Body style
{0pt}% Heading indent amount
{\bfseries}{.}% Heading font and punctuation after it
{1ex plus 0pt minus .2ex}% Space after heading 
{}% Head spec (empty = same as ‘plain’ style
\theoremstyle{definition}
\newtheorem{Def}{\@defname}[chapter]
\theoremstyle{theo}
\newtheorem{Axi}{\@axiomname}[chapter]
\newtheorem{Conj}{\@conjecturename}[chapter]
\newtheorem{Lem}{\@lemmaname}[chapter]
\newtheorem{Theo}{\@theoname}[chapter]
\newtheorem{Prop}{\@propname}[chapter]
\newtheorem{Cor}{\@corname}[chapter]

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Reference macros
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\newcommand{\figref}[2][]{\@figurename~\ref{#2}#1\xspace}
\newcommand{\tabref}[1]{\@tablename~\ref{#1}\xspace}
\newcommand{\eqnref}[1]{\@equationame~\eqref{#1}\xspace}
\newcommand{\chapref}[1]{\@chaptername~\ref{#1}\xspace}
\newcommand{\secref}[1]{\@sectionname~\ref{#1}\xspace}
\newcommand{\appref}[1]{\@appendixname~\ref{#1}\xspace}
\newcommand{\axiref}[1]{\@axiomname~\ref{#1}\xspace}
\newcommand{\conjref}[1]{\@conjname~\ref{#1}\xspace}
\newcommand{\defref}[1]{\@defname~\ref{#1}\xspace}
\newcommand{\lemref}[1]{\@lemmaname~\ref{#1}\xspace}
\newcommand{\theoref}[1]{\@theoname~\ref{#1}\xspace}
\newcommand{\corref}[1]{\@corname~\ref{#1}\xspace}
\newcommand{\propref}[1]{\@propname~\ref{#1}\xspace}
\newcommand{\pgref}[1]{\@pagename~\pageref{#1}\xspace}

