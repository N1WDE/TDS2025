import { useMemo, useState } from 'react';
import {
  IonApp,
  IonContent,
  IonInput,
  IonButton,
  IonIcon,
} from '@ionic/react';
import { arrowDown, arrowForward, refreshOutline, swapHorizontalOutline } from 'ionicons/icons';

const RATE = 8;
const CARNET = '25006933';

function formatCurrency(value: number, currency: 'GTQ' | 'USD') {
  return new Intl.NumberFormat('es-GT', {
    style: 'currency',
    currency,
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  }).format(value);
}

export default function App() {
  const [quetzales, setQuetzales] = useState('25006933');
  const numericAmount = useMemo(() => {
    const parsed = Number(quetzales);
    return Number.isFinite(parsed) && parsed >= 0 ? parsed : 0;
  }, [quetzales]);
  const dollars = numericAmount / RATE;

  const handleReset = () => setQuetzales('');

  return (
    <IonApp>
      <IonContent fullscreen className="app-content">
        <main className="page-shell">
          <header className="topbar">
            <div className="brand-lockup">
              <div className="brand-icon"><span>Q</span><span className="brand-dollar">$</span></div>
              <div>
                <div className="brand-name">Divis<span>App</span></div>
                <div className="brand-caption">CONVERSOR DE DIVISAS</div>
              </div>
            </div>
            <div className="student-tag">CARNÉ {CARNET}</div>
          </header>

          <section className="intro">
            <div className="eyebrow"><span className="eyebrow-dot" /> CONVERSIÓN SIMPLE Y RÁPIDA</div>
            <h1>DivisApp <span>{CARNET}</span></h1>
            <p>Convierte tus quetzales a dólares en segundos.</p>
          </section>

          <section className="converter-card" aria-label="Conversor de quetzales a dólares">
            <div className="card-topline">
              <div>
                <span className="section-kicker">CONVERTIR DE</span>
                <h2>Quetzales a dólares</h2>
              </div>
              <div className="live-pill"><span /> TASA FIJA</div>
            </div>

            <div className="amount-panel source-panel">
              <div className="currency-info">
                <div className="currency-flag guatemala-flag"><span /></div>
                <div><strong>Quetzal</strong><small>GTQ · Guatemala</small></div>
              </div>
              <label className="amount-label" htmlFor="amount-input">CANTIDAD EN QUETZALES</label>
              <div className="amount-input-wrap">
                <span className="currency-symbol">Q</span>
                <IonInput
                  id="amount-input"
                  className="amount-input"
                  type="number"
                  inputmode="decimal"
                  min="0"
                  step="0.01"
                  value={quetzales}
                  placeholder="0.00"
                  aria-label="Cantidad en quetzales"
                  onIonInput={(event) => setQuetzales(event.detail.value ?? '')}
                />
              </div>
              <div className="input-help">Ingresa la cantidad que deseas convertir</div>
            </div>

            <div className="rate-connector">
              <div className="connector-line" />
              <div className="swap-bubble"><IonIcon icon={arrowDown} /></div>
              <div className="rate-label">1 USD = 8 GTQ</div>
              <div className="connector-line" />
            </div>

            <div className="amount-panel result-panel">
              <div className="currency-info">
                <div className="currency-flag usa-flag"><span /></div>
                <div><strong>Dólar estadounidense</strong><small>USD · Estados Unidos</small></div>
              </div>
              <div className="amount-label">EQUIVALENTE EN DÓLARES</div>
              <div className="result-amount" aria-live="polite">
                <span className="result-symbol">$</span>
                <span>{formatCurrency(dollars, 'USD').replace('US$', '').replace('$', '').trim()}</span>
              </div>
              <div className="result-footnote">Resultado calculado automáticamente</div>
            </div>

            <div className="rate-summary">
              <div className="rate-summary-icon"><IonIcon icon={swapHorizontalOutline} /></div>
              <div><strong>Tipo de cambio aplicado</strong><small>1 dólar equivale a 8 quetzales</small></div>
              <div className="rate-value">Q8.00</div>
            </div>

            <IonButton expand="block" fill="clear" className="reset-button" onClick={handleReset}>
              <IonIcon icon={refreshOutline} slot="start" /> Limpiar cantidad
            </IonButton>
          </section>

          <footer className="page-footer">
            <div className="footer-mark">DA</div>
            <div><strong>DivisApp · Proyecto académico</strong><span>Conversión de GTQ a USD · Carné {CARNET}</span></div>
            <div className="footer-arrow"><IonIcon icon={arrowForward} /></div>
          </footer>
          <div className="bottom-note">Tasa de conversión establecida para esta actividad: Q8.00 por US$1.00</div>
        </main>
      </IonContent>
    </IonApp>
  );
}
